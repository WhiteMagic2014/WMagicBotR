package com.whitemagic2014.service;

import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcr.Battle;
import com.whitemagic2014.pojo.pcr.Notice;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;

import java.util.List;
import java.util.Map;

/**
 * @Description: pcr 公会战 service
 * @waring: 为了兼容以后可能有的web端, 存在判断,权限判断需要外置, 在调用的部分判断(web在 controller下, 群聊在command下)
 * @author: magic chen
 * @date: 2020/8/22 13:53
 **/
public interface PcrBotService {


    /**
     * @Name: createGuild
     * @Description: 创建公会
     * @Param: gid  q群号
     * @Param: gname 公会名
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 21:06
     **/
    PrivateModel<String> createGuild(Long gid, String gname);

    /**
     * @Name: deleteGuild
     * @Description: 人生有梦 各自精彩
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 17:17
     **/
    PrivateModel<String> deleteGuild(Long gid);


    /**
     * @Name: clearGuildData
     * @Description: 清理工会战数据 不删除工会
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/23 12:16
     **/
    PrivateModel<String> clearGuildData(Long gid);


    /**
     * @Name: addMemer
     * @Description: 添加某人进公会
     * @Param: gid  公会群号
     * @Param: uid  目标成员qq
     * @Param: uname  目标成员群昵称
     * @Param: permission  目标成员 群组权限
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 21:12
     **/
    PrivateModel<String> addMemer(Long gid, Long uid, String uname, MemberPermission permission);


    /**
     * @Name: removeMemer
     * @Description: 删除某公会成员
     * @Param: gid
     * @Param: uid
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/23 11:42
     **/
    PrivateModel<String> removeMemer(Long gid, Long uid);

    /**
     * @Name: addAllMemer
     * @Description: 添加所有人进公会  这个功能仅在群聊下可用，web不可用
     * @Param: group
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 17:28
     **/
    PrivateModel<String> addAllMemer(Group group);


    /**
     * @Name: attackKnife
     * @Description: 出刀 必须刷新刀状态
     * @Param: gid 群组
     * @Param: uid 人员
     * @Param: damage 伤害
     * @Param: yesterday 是否报昨日刀
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 18:44
     **/
    PrivateModel<String> attackKnife(Long gid, Long uid, Long damage, Boolean yesterday);

    /**
     * @Name: endKnife
     * @Description: 出尾刀 无需刷新刀状态
     * @Param: gid 群组
     * @Param: uid 人员
     * @Param: yesterday 是否昨日刀
     * @Return:
     * @Author: magic chen
     * @Date: 2020/8/22 18:53
     **/
    PrivateModel<Map<String, String>> endKnife(Long gid, Long uid, Boolean yesterday);


    /**
     * @Name: checkKnife
     * @Description: 查刀
     * @Param: gid
     * @Param: findall  true查询所有刀,false查询当天刀
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/24 23:25
     **/
    PrivateModel<List<Battle>> checkKnife(Long gid, Boolean findall);

    /**
     * @Name: checkBossState
     * @Description: 查询boss状态
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/23 12:47
     **/
    PrivateModel<String> checkBossState(Long gid);

    /**
     * @Name: updateBossState
     * @Description: 直接修改boss状态
     * @Param: gid
     * @Param: uid
     * @Param: cycle  几周目
     * @Param: num  几王
     * @Param: hpnow 现在血量 null则为满血
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/28 16:45
     **/
    PrivateModel<String> updateBossState(Long gid, Long uid, Integer cycle, Integer num, Long hpnow);

    /**
     * @Name: cancelKnfie
     * @Description: 撤销最后一次报刀
     * @Param: gid
     * @Param: sender 注意这个不是要撤销这个人，而是这个发送人，要看有无权限撤销(自己可以撤销自己,管理员可以撤销所有)
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 18:54
     **/
    PrivateModel<String> cancelKnfie(Long gid, Long sender);

    /**
     * @Name: sl
     * @Description: boss sl
     * @Param: gid
     * @Param: uid
     * @Param: true 为查询sl false为记录sl
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 18:56
     **/
    PrivateModel<String> sl(Long gid, Long uid, Boolean check);


    /**
     * @Name: orderBoss
     * @Description: 预约boss
     * @Param: gid
     * @Param: uid
     * @Param: bossNum 预约的是几王
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 19:00
     **/
    PrivateModel<String> orderBoss(Long gid, Long uid, Integer bossNum);


    /**
     * @Name: cancelOrder
     * @Description: 取消预约boss
     * @Param: gid
     * @Param: uid
     * @Param: num
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/23 12:06
     **/
    PrivateModel<String> cancelOrder(Long gid, Long uid, Integer num);

    /**
     * @Name: hangOnTree
     * @Description: 挂树
     * @Param: gid
     * @Param: uid
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/22 19:02
     **/
    PrivateModel<String> hangOnTree(Long gid, Long uid);


    /**
     * @Name: checkTree
     * @Description: 查询挂树
     * @Param: gid
     * @Return:
     * @Author: magic chen
     * @Date: 2020/8/23 12:30
     **/
    PrivateModel<List<Long>> checkTree(Long gid);

    /**
     * @Name: checkOrder
     * @Description: 查询预约
     * @Param: gid
     * @Return:
     * @Author: magic chen
     * @Date: 2020/8/23 12:31
     **/
    PrivateModel<Map<Integer, List<Notice>>> checkOrder(Long gid);


    /**
     * @Name: userIsOwner
     * @Description: 是否是bot主人
     * @Param: gid
     * @Param: uid
     * @Return: java.lang.Boolean
     * @Author: magic chen
     * @Date: 2020/8/23 13:04
     **/
    Boolean userIsOwner(Long gid, Long uid);

    /**
     * @Name: userIsAdmin
     * @Description: 是否是bot管理员
     * @Param: gid
     * @Param: uid
     * @Return: java.lang.Boolean
     * @Author: magic chen
     * @Date: 2020/8/23 13:05
     **/
    Boolean userIsAdmin(Long gid, Long uid);


    /**
     * @Name: bossLock
     * @Description: 群内锁定boss 锁定3分钟,超时自动解锁
     * @Param: gid qq群号
     * @Param: uid 锁定用户的人
     * @Param: uname 用户名
     * @Param: desc 是申请出刀还是其他留言什么的
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/23 10:04
     **/
    PrivateModel<String> bossLock(Long gid, Long uid, String uname, String desc);


    /**
     * @Name: removeLock
     * @Description: 移除某群内boss锁定
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/23 10:18
     **/
    PrivateModel<String> removeLock(Long gid);

    /**
     * @Name: checkLock
     * @Description: 检查是否有群内boss锁定
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/23 10:20
     **/
    PrivateModel<String> checkLock(Long gid);
}
