package com.whitemagic2014.service.impl;

import com.whitemagic2014.dao.PcrDao;
import com.whitemagic2014.dic.*;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcr.*;
import com.whitemagic2014.service.PcrBotService;
import com.whitemagic2014.util.MagicHelper;
import com.whitemagic2014.util.MagicMaps;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description: pcr 公会战 service 实现类
 * @waring: 为了兼容以后可能有的web端, 权限判断需要外置, 在调用的部分判断(web在 controller下, 群聊在command或event下)
 * @waring: 工会战部分操作需要加锁
 * @author: magic chen
 * @date: 2020/8/22 20:07
 **/
@Service
public class PcrBotServiceImpl implements PcrBotService {

    @Autowired
    PcrDao pcrDao;

    @Value("${bot.admin}")
    Long adminUid;


    private static final Logger logger = LoggerFactory.getLogger(PcrBotServiceImpl.class);

    @Override
    public PrivateModel<String> createGuild(Long gid, String gname) {
        Guild guild = pcrDao.findGuildByGid(gid);
        if (guild != null) {
            return new PrivateModel<>(ReturnCode.FAIL, "公会已经创建");
        }
        guild = new Guild();
        guild.setGid(gid);
        guild.setGname(gname);
        pcrDao.addGuile(guild);

        //初始化一周目一王
        Boss boss = new Boss();
        boss.setGid(gid);
        boss.setCycle(1);
        boss.setNum(1);
        boss.setHp(Dic.BossHp[0]);
        boss.setHpnow(Dic.BossHp[0]);
        boss.setActive(true);
        pcrDao.addBoss(boss);

        return new PrivateModel<>(ReturnCode.SUCCESS, "success", "创建公会[" + gname + "]成功");
    }

    @Override
    public PrivateModel<String> deleteGuild(Long gid) {

        // 检查工会是否存在
        PrivateModel<Guild> checkguild = checkGuildExist(gid);
        if (!checkguild.isSuccess()) {
            return new PrivateModel<String>().wrapper(checkguild);
        }

        // 删公会
        pcrDao.deleteGuile(gid);
        // 删公会下通知
        Notice notice = new Notice();
        notice.setGid(gid);
        pcrDao.deleteNotice(notice);
        // 删公会下boss
        pcrDao.deleteBossByGid(gid);
        // 删公会下出刀记录
        pcrDao.deleteBattleByGid(gid);
        // 删公会下成员
        pcrDao.deleteUserByGid(gid);

        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "已删除公会,人生有梦 各自精彩！");

    }

    @Override
    public PrivateModel<String> clearGuildData(Long gid) {

        // 检查工会是否存在
        PrivateModel<Guild> checkguild = checkGuildExist(gid);
        if (!checkguild.isSuccess()) {
            return new PrivateModel<String>().wrapper(checkguild);
        }

        // 删公会下通知
        Notice notice = new Notice();
        notice.setGid(gid);
        pcrDao.deleteNotice(notice);
        // 删公会下boss
        pcrDao.deleteBossByGid(gid);
        // 删公会下出刀记录
        pcrDao.deleteBattleByGid(gid);


        //初始化一周目一王
        Boss boss = new Boss();
        boss.setGid(gid);
        boss.setCycle(1);
        boss.setNum(1);
        boss.setHp(Dic.BossHp[0]);
        boss.setHpnow(Dic.BossHp[0]);
        boss.setActive(true);
        pcrDao.addBoss(boss);

        User reset = new User();
        reset.setSl(false);
        reset.setGid(gid);
        pcrDao.updateUserByGid(reset);
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "已重置公会战所有记录");
    }

    @Override
    public PrivateModel<String> addMemer(Long gid, Long uid, String uname, MemberPermission permission) {
        // 检查工会是否存在
        PrivateModel<Guild> checkguild = checkGuildExist(gid);
        if (!checkguild.isSuccess()) {
            return new PrivateModel<String>().wrapper(checkguild);
        }
        // 发送消息的人
        User ori = pcrDao.findUserByUid(gid, uid);
        if (ori != null) {
            return new PrivateModel<>(ReturnCode.FAIL,
                    "用户 [" + uid + "," + ori.getUname() + "] 已经加入公会");
        } else {
            User user = createUser(gid, uid, uname, permission);
            pcrDao.addUser(user);
            return new PrivateModel<>(ReturnCode.SUCCESS,
                    "success",
                    "用户 [" + uid + "," + uname + "] 添加成功");
        }
    }


    @Override
    public PrivateModel<String> removeMemer(Long gid, Long uid) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }
        // 查目标人员在不在
        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }

        //这里出刀记录保留
        pcrDao.deleteUser(gid, uid);
        return new PrivateModel<>(ReturnCode.FAIL,
                "用户 [" + uid + "] 已经离开公会");
    }

    @Override
    public PrivateModel<String> addAllMemer(Group group) {
        // 检查工会是否存在
        PrivateModel<Guild> checkguild = checkGuildExist(group.getId());
        if (!checkguild.isSuccess()) {
            return new PrivateModel<String>().wrapper(checkguild);
        }

        List<User> userList = pcrDao.findUserByGid(group.getId());
        Set<Long> userExist = userList.stream().map(User::getUid).collect(Collectors.toSet());
        // 添加用户
        group.getMembers().stream().filter(member -> !userExist.contains(member.getId()))
                .map(member -> createUser(group.getId(), member.getId(), member.getNick(), member.getPermission()))
                .forEach(pcrDao::addUser);

        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "已将本群成员 加入[" + group.getName() + "]公会");
    }


    @Override
    public PrivateModel<String> attackKnife(Long gid, Long uid, Long damage, Boolean yesterday) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }
        // 查被报刀的人在不在
        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }

        if (damage < 0L) {
            return new PrivateModel<>(ReturnCode.FAIL, "指令错误,报刀伤害需要大于等于0,不然你报啥呢？");
        }

        User user = pcrDao.findUserByUid(gid, uid);
        String pcrDay;
        String daystr;
        if (yesterday) {
            pcrDay = MagicHelper.pcrYesterday();
            daystr = "昨日";
        } else {
            pcrDay = MagicHelper.pcrToday();
            daystr = "今日";
        }
        // 今日非补偿刀数量
        int knifeNum = pcrDao.checkKnifeNum(gid, uid, pcrDay);
        // 用户上一刀
        Battle lastKnife = pcrDao.findLastBattleSelf(gid, uid);

        if (knifeNum >= 3 && lastKnife.getType().isNotEnd()) {
            return new PrivateModel<>(ReturnCode.FAIL, "[" + user.getUname() + "] " +
                    daystr + "三刀已经出完,请不要重复报刀");
        }
        Boss bossActive = pcrDao.findBossActiveByGid(gid);
        // 检查血量是否应该报尾刀
        if (bossActive.getHpnow() < damage) {
            return new PrivateModel<>(ReturnCode.FAIL, "伤害超出剩余血量，如击败请使用尾刀");
        }
        Boss bossUpdate = new Boss();
        bossUpdate.setId(bossActive.getId());
        // 目前剩余
        Long remainHp = bossActive.getHpnow() - damage;
        // 更新boss血量
        bossUpdate.setHpnow(remainHp);
        pcrDao.updateBoss(bossUpdate);


        // 记录出刀记录
        Battle battle = new Battle();
        battle.setBossid(bossActive.getId());
        battle.setUid(uid);
        battle.setUname(user.getUname());
        battle.setGid(gid);
        battle.setDamage(damage);
        battle.setTime(pcrDay);
        battle.setKillboss(false);

        String knife;
        int knifeNumNotice;
        if (lastKnife != null && lastKnife.getType().isEnd()) {
            knife = "补偿刀";
            battle.setType(BattleType.extra);
            knifeNumNotice = knifeNum;
        } else {
            knife = "完整刀";
            battle.setType(BattleType.nomal);
            knifeNumNotice = knifeNum + 1;
        }
        pcrDao.addBattle(battle);

        // 该用户出刀后删除申请出刀的锁定
        if (MagicMaps.check(BossLock.getLockname(gid))) {
            BossLock lock = MagicMaps.get(BossLock.getLockname(gid), BossLock.class);
            if (lock != null && lock.getUid().equals(uid) && lock.getDesc().equals(BossLock.request)) {
                MagicMaps.remove(BossLock.getLockname(gid));
            }
        }

        String result = "[" + user.getUname() + "]对boss造成了" + MagicHelper.longAddComma(damage) + "点伤害\n" +
                "（" + daystr + "第" + knifeNumNotice + "刀," + knife + "）\n" +
                "现在" + bossActive.getCycle() + "周目," + bossActive.getNum() + "号boss\n" +
                "生命值" + MagicHelper.longAddComma(remainHp);
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                result);
    }

    @Override
    public PrivateModel<Map<String, String>> endKnife(Long gid, Long uid, Boolean yesterday) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<Map<String, String>>().wrapper(gexist);
        }
        // 查被报刀的人在不在
        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<Map<String, String>>().wrapper(uexist);
        }

        User user = pcrDao.findUserByUid(gid, uid);
        String pcrDay;
        String daystr;
        if (yesterday) {
            pcrDay = MagicHelper.pcrYesterday();
            daystr = "昨日";
        } else {
            pcrDay = MagicHelper.pcrToday();
            daystr = "今日";
        }
        // 今日非补偿刀数量
        int knifeNum = pcrDao.checkKnifeNum(gid, uid, pcrDay);
        System.out.println("knifeNum：" + knifeNum);
        System.out.println("pcrDay：" + pcrDay);

        // 用户上一刀
        Battle lastKnife = pcrDao.findLastBattleSelf(gid, uid);

        if (knifeNum >= 3 && lastKnife.getType().isNotEnd()) {
            return new PrivateModel<>(ReturnCode.FAIL, "[" + user.getUname() + "] " +
                    daystr + "三刀已经出完,请不要重复报刀");
        }

        Boss bossActive = pcrDao.findBossActiveByGid(gid);
        // 尾刀伤害等于 现在的血量
        Long damage = bossActive.getHpnow();

        // 击败原本boss
        Boss bossUpdate = new Boss();
        bossUpdate.setId(bossActive.getId());
        bossUpdate.setActive(false);
        bossUpdate.setHpnow(0L);
        pcrDao.updateBoss(bossUpdate);

        // 创建新的boss
        Boss bossNew = nextBoss(bossActive);
        pcrDao.addBoss(bossNew);

        // 记录出刀记录
        Battle battle = new Battle();
        battle.setBossid(bossActive.getId());
        battle.setUid(uid);
        battle.setUname(user.getUname());
        battle.setGid(gid);
        battle.setDamage(damage);
        battle.setTime(pcrDay);
        battle.setKillboss(true);

        String knife;
        int knifeNumNotice;
        if (lastKnife != null && lastKnife.getType().isEnd()) {
            // 上一刀是尾刀 这一刀是补偿刀收尾 下一刀不是补偿刀
            battle.setType(BattleType.extra);
            knife = "补偿刀收尾,下一刀无补偿";
            knifeNumNotice = knifeNum;
        } else {
            // 上一刀不是尾刀 这刀不是补偿刀,是尾刀
            battle.setType(BattleType.end);
            knife = "收尾刀,下一刀补偿刀";
            knifeNumNotice = knifeNum + 1;
        }

        pcrDao.addBattle(battle);

        // 该用户出刀后删除申请出刀的锁定
        if (MagicMaps.check(BossLock.getLockname(gid))) {
            BossLock lock = MagicMaps.get(BossLock.getLockname(gid), BossLock.class);
            if (lock != null && lock.getUid().equals(uid) && lock.getDesc().equals(BossLock.request)) {
                MagicMaps.remove(BossLock.getLockname(gid));
            }
        }

        //挂树和预约通知 需要@的人
        Notice tree = new Notice();
        tree.setGid(gid);
        tree.setType(PcrNoticeType.tree);
        List<Notice> ontree = pcrDao.findNoticeByConditions(tree);

        Notice order = new Notice();
        order.setGid(gid);
        order.setType(PcrNoticeType.order);
        order.setBossNum(bossNew.getNum());
        List<Notice> onOrder = pcrDao.findNoticeByConditions(order);

        // 当通知触发后需要删除通知
        pcrDao.deleteNotice(tree);
        pcrDao.deleteNotice(order);


        Map<String, List<Long>> ats = new HashMap<>();
        if (!ontree.isEmpty()) {
            ats.put(PcrNoticeType.tree.name(), ontree.stream().map(Notice::getUid).collect(Collectors.toList()));
        }
        if (!onOrder.isEmpty()) {
            ats.put(PcrNoticeType.order.name(), onOrder.stream().map(Notice::getUid).collect(Collectors.toList()));
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("nomal", "[" + user.getUname() + "]对boss造成了" + MagicHelper.longAddComma(damage) + "点伤害,击败了boss\n" +
                "（" + daystr + "第" + knifeNumNotice + "刀," + knife + "）\n" +
                "现在" + bossNew.getCycle() + "周目," + bossNew.getNum() + "号boss\n" +
                "生命值" + MagicHelper.longAddComma(bossNew.getHpnow()));
        resultMap.put(PcrNoticeType.tree.name(), "当前boss已被击败,您已下树");
        resultMap.put(PcrNoticeType.order.name(), "您预约的boss已经出现,可以出刀了");
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success", ats,
                resultMap);
    }


    @Override
    public PrivateModel<List<Battle>> checkKnife(Long gid, Boolean findall) {

        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<List<Battle>>().wrapper(gexist);
        }

        Battle cond = new Battle();
        if (!findall) {
            cond.setTime(MagicHelper.pcrToday());
        }
        cond.setGid(gid);
        List<Battle> datas = pcrDao.findBattleByConditions(cond);
        return new PrivateModel<>(ReturnCode.SUCCESS, "success", datas);
    }


    @Override
    public PrivateModel<String> checkBossState(Long gid) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }

        Boss active = pcrDao.findBossActiveByGid(gid);

        String result = "\n现在" + active.getCycle() + "周目," + active.getNum() + "号boss\n" +
                "生命值" + MagicHelper.longAddComma(active.getHpnow());

        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                result);
    }

    @Override
    public PrivateModel<String> updateBossState(Long gid, Long uid, Integer cycle, Integer num, Long hpnow) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }

        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }

        if (num > 5 || num < 1) return new PrivateModel<>(ReturnCode.FAIL, "指令错误,请输入正确的1~5王");
        Long hpmax = Dic.BossHp[num - 1];
        if (hpnow == null) hpnow = hpmax;
        if (hpmax < hpnow)
            return new PrivateModel<>(ReturnCode.FAIL, "指令错误,请输入正确的血量," + num + "王最多" + MagicHelper.longAddComma(hpmax) + "hp");

        Boss newboss = new Boss();
        newboss.setGid(gid);
        newboss.setCycle(cycle);
        newboss.setNum(num);
        newboss.setHp(hpmax);
        newboss.setHpnow(hpnow);
        newboss.setActive(true);
        // 需要删除 这个boss 以及所有这个boss 后面的boss 否则在撤销尾刀的时候会出问题
        pcrDao.deleteBossAfterNow(newboss);
        // 加入新修改的boss
        pcrDao.addBoss(newboss);
        String result = "修改成功\n现在" + newboss.getCycle() + "周目," + newboss.getNum() + "号boss\n" +
                "生命值" + MagicHelper.longAddComma(newboss.getHpnow());
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                result);
    }

    @Override
    public PrivateModel<String> cancelKnfie(Long gid, Long sender) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }
        // 查目标人员在不在
        PrivateModel<User> uexist = checkUserExist(gid, sender);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }
        User userSender = uexist.getReturnObject();

        // 整个工会 上一刀
        Battle lastKnife = pcrDao.findLastBattleGuild(gid);
        if (lastKnife == null) {
            return new PrivateModel<>(ReturnCode.FAIL, "已经没有可以撤回的刀了");
        }
        // 仅本人可以撤回 或者管理员撤回
        if (lastKnife.getUid() != sender && !userSender.getRole().isAdmin()) {
            return new PrivateModel<>(ReturnCode.FAIL,
                    "非管理员只能撤销自己的刀,上一刀用户" + lastKnife.getUname() + "[" + lastKnife.getUid() + "]");
        }
        Boss active = pcrDao.findBossActiveByGid(gid);
        int cycle, num;
        Long hpnow;
        if (lastKnife.getKillboss()) {
            // 需要回滚至上一个boss
            Boss last = lastBoss(active);
            Boss lastUpdate = new Boss();
            lastUpdate.setId(last.getId());
            lastUpdate.setHpnow(lastKnife.getDamage());
            lastUpdate.setActive(true);
            pcrDao.updateBoss(lastUpdate);
            cycle = last.getCycle();
            num = last.getNum();
            hpnow = lastKnife.getDamage();

            // 删除当前boss
            pcrDao.deleteBossById(active.getId());
        } else {
            // 仅需要回复boss血量
            Boss activeUpdate = new Boss();
            activeUpdate.setId(active.getId());
            activeUpdate.setHpnow(active.getHpnow() + lastKnife.getDamage());
            cycle = active.getCycle();
            num = active.getNum();
            hpnow = active.getHpnow() + lastKnife.getDamage();
            pcrDao.updateBoss(activeUpdate);
        }

        // 删除出刀记录
        pcrDao.deleteBattle(lastKnife.getId());

        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "\n已经撤销" + lastKnife.getUname() + "[" + lastKnife.getUid() + "]的出刀,现在" + cycle + "周目," + num + "号boss\n" +
                        "生命值" + MagicHelper.longAddComma(hpnow));
    }

    @Override
    public PrivateModel<String> sl(Long gid, Long uid, Boolean check) {
        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }
        // 查目标人员在不在
        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }

        if (check) {
            User user = pcrDao.findUserByUid(gid, uid);
            String result;
            if (user.getSl()) {
                result = "今日已经sl";
            } else {
                result = "今日未sl";
            }
            return new PrivateModel<>(ReturnCode.SUCCESS,
                    "success",
                    result);
        } else {
            User update = new User();
            update.setUid(uid);
            update.setSl(true);
            update.setGid(gid);
            pcrDao.updateUser(update);
            return new PrivateModel<>(ReturnCode.SUCCESS,
                    "success",
                    "已经记录sl");
        }


    }

    @Override
    public PrivateModel<String> orderBoss(Long gid, Long uid, Integer bossNum) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }
        // 查目标人员在不在
        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }

        Notice condition = new Notice();
        condition.setUid(uid);
        condition.setGid(gid);
        condition.setType(PcrNoticeType.order);
        List<Notice> list = pcrDao.findNoticeByConditions(condition);
        if (!list.isEmpty()) {
            return new PrivateModel<>(ReturnCode.FAIL,
                    "您已预约" + list.get(0).getBossNum() + "号boss,请不要重复多次预约");
        }
        Notice order = new Notice();
        order.setGid(gid);
        order.setBossNum(bossNum);
        order.setUid(uid);
        order.setType(PcrNoticeType.order);
        pcrDao.addNotice(order);
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "您已成功预约,该boss出现会即时通知");
    }

    @Override
    public PrivateModel<String> cancelOrder(Long gid, Long uid, Integer num) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }
        // 查目标人员在不在
        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }

        Notice del = new Notice();
        del.setGid(gid);
        del.setUid(uid);
        del.setBossNum(num);
        pcrDao.deleteNotice(del);
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "您的预约已取消");
    }

    @Override
    public PrivateModel<String> hangOnTree(Long gid, Long uid) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }
        // 查目标人员在不在
        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }


        Notice condition = new Notice();
        condition.setUid(uid);
        condition.setGid(gid);
        condition.setType(PcrNoticeType.tree);
        List<Notice> list = pcrDao.findNoticeByConditions(condition);
        if (!list.isEmpty()) {
            return new PrivateModel<>(ReturnCode.FAIL,
                    "您还没从树上下来,怎么又的上树?");
        }

        Notice tree = new Notice();
        tree.setType(PcrNoticeType.tree);
        tree.setGid(gid);
        tree.setUid(uid);
        pcrDao.addNotice(tree);
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "已上树,当前boss被击败会即时通知");
    }


    @Override
    public PrivateModel<List<Long>> checkTree(Long gid) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<List<Long>>().wrapper(gexist);
        }
        Notice cond = new Notice();
        cond.setGid(gid);
        cond.setType(PcrNoticeType.tree);

        List<Notice> ontree = pcrDao.findNoticeByConditions(cond);

        if (ontree.isEmpty()) {
            return new PrivateModel<>(ReturnCode.FAIL,
                    "树上没人");
        } else {
            return new PrivateModel<>(ReturnCode.SUCCESS,
                    "success",
                    ontree.stream().map(Notice::getUid).collect(Collectors.toList()));
        }

    }

    @Override
    public PrivateModel<Map<Integer, List<Notice>>> checkOrder(Long gid) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<Map<Integer, List<Notice>>>().wrapper(gexist);
        }

        Notice cond = new Notice();
        cond.setGid(gid);
        cond.setType(PcrNoticeType.order);

        List<Notice> order = pcrDao.findNoticeByConditions(cond);

        if (order.isEmpty()) {
            return new PrivateModel<>(ReturnCode.FAIL,
                    "还没有人预约boss");
        } else {
            return new PrivateModel<>(ReturnCode.SUCCESS,
                    "success", order.stream().collect(Collectors.groupingBy(Notice::getBossNum)));
        }

    }

    @Override
    public PrivateModel<String> bossLock(Long gid, Long uid, String uname, String desc) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }
        // 查目标人员在不在
        PrivateModel<User> uexist = checkUserExist(gid, uid);
        if (!uexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(uexist);
        }
        // 查boss有没有被锁定
        PrivateModel<String> checklock = checkLock(gid);
        if (!checklock.isSuccess()) {
            return new PrivateModel<String>().wrapper(checklock);
        }


        MagicMaps.putWithExpire(BossLock.getLockname(gid), new BossLock(uid, uname, desc), 3L, TimeUnit.MINUTES);
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "已经锁定boss,锁定时间3分钟,超时自动解锁");
    }

    @Override
    public PrivateModel<String> removeLock(Long gid) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }

        MagicMaps.remove(BossLock.getLockname(gid));
        return new PrivateModel<>(ReturnCode.SUCCESS,
                "success",
                "已解锁");
    }

    @Override
    public PrivateModel<String> checkLock(Long gid) {

        // 查工会在不在
        PrivateModel<Guild> gexist = checkGuildExist(gid);
        if (!gexist.isSuccess()) {
            return new PrivateModel<String>().wrapper(gexist);
        }

        BossLock lock = MagicMaps.get(BossLock.getLockname(gid), BossLock.class);

        if (lock != null) {
            // 有锁定
            return new PrivateModel<>(ReturnCode.FAIL,
                    "当前boss被锁定,用户[" + lock.getUname() + "]" + (lock.getDesc().equals(BossLock.request) ? " 申请出刀中" : "留言: " + lock.getDesc()));
        } else {
            // 当前无锁定
            return new PrivateModel<>(ReturnCode.SUCCESS,
                    "success");
        }
    }


    @Override
    public Boolean userIsOwner(Long gid, Long uid) {
        User user = pcrDao.findUserByUid(gid, uid);
        if (user == null) {
            return false;
        } else {
            return user.getRole().isOwner();
        }
    }

    @Override
    public Boolean userIsAdmin(Long gid, Long uid) {
        User user = pcrDao.findUserByUid(gid, uid);
        if (user == null) {
            return false;
        } else {
            return user.getRole().isAdmin();
        }
    }

    private PrivateModel<User> checkUserExist(Long gid, Long uid) {
        User user = pcrDao.findUserByUid(gid, uid);
        if (user == null) {
            return new PrivateModel<>(ReturnCode.FAIL, "用户[" + uid + "]还未加入公会,请先加入公会");
        } else {
            return new PrivateModel<>(ReturnCode.SUCCESS, "success", user);
        }
    }

    private PrivateModel<Guild> checkGuildExist(Long gid) {
        Guild guild = pcrDao.findGuildByGid(gid);
        if (guild == null) {
            return new PrivateModel<>(ReturnCode.FAIL, "还未创建公会,请先创建公会");
        } else {
            return new PrivateModel<>(ReturnCode.SUCCESS, "success", guild);
        }
    }


    /**
     * @Name: nextBoss
     * @Description: 获得下一个boss 实例
     * @Param: cycle
     * @Param: num
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.pcr.Boss
     * @Author: magic chen
     * @Date: 2020/8/22 23:57
     **/
    private Boss nextBoss(Boss last) {
        int nextNum;
        int nextCycle;
        if (last.getNum() == 5) {
            nextNum = 1;
            nextCycle = last.getCycle() + 1;
        } else {
            nextNum = last.getNum() + 1;
            nextCycle = last.getCycle();
        }
        Boss boss = new Boss();
        boss.setGid(last.getGid());
        boss.setCycle(nextCycle);
        boss.setNum(nextNum);
        boss.setHp(Dic.BossHp[nextNum - 1]);
        boss.setHpnow(Dic.BossHp[nextNum - 1]);
        boss.setActive(true);
        return boss;
    }


    /**
     * @Name: lastBossCondition
     * @Description: 撤销尾刀时 需要回退boss
     * @Param: active
     * @Return: com.whitemagic2014.pojo.pcr.Boss
     * @Author: magic chen
     * @Date: 2020/8/23 09:07
     **/
    private Boss lastBoss(Boss active) {
        if (active.getNum() == 1) {
            // 需要回退至上一周目
            return pcrDao.findBossDefineByGid(active.getGid(), active.getCycle() - 1, 5);
        } else {
            // 周目不变 boss 回退1
            return pcrDao.findBossDefineByGid(active.getGid(), active.getCycle(), active.getNum() - 1);
        }
    }


    /**
     * @Name: createUser
     * @Description: 构造用户对象
     * @Param: gid
     * @Param: uid
     * @Param: uname
     * @Param: permission
     * @Return: com.whitemagic2014.pojo.pcr.User
     * @Author: magic chen
     * @Date: 2020/8/23 10:22
     **/
    private User createUser(Long gid, Long uid, String uname, MemberPermission permission) {
        User temp = new User();
        temp.setUid(uid);
        temp.setUname(uname);
        temp.setGid(gid);
        if (uid.equals(adminUid)) {
            temp.setRole(UserRole.OWNER);
        } else if (permission == MemberPermission.OWNER || permission == MemberPermission.ADMINISTRATOR) {
            temp.setRole(UserRole.ADMIN);
        } else {
            temp.setRole(UserRole.MEMBER);
        }
        temp.setSl(false);
        return temp;
    }


}
