package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.pcr.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: pcr dao
 * @author: magic chen
 * @date: 2020/8/22 13:52
 **/
@Repository
public interface PcrDao {


    /**
     * @Name: addUser
     * @Description: 新增用户
     * @Param: user
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 15:34
     **/
    int addUser(User user);

    /**
     * @Name: deleteUser
     * @Description: 删除用户
     * @param: gid 群号
     * @Param: uid qq号
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 15:34
     **/
    int deleteUser(@Param("gid") Long gid, @Param("uid") Long uid);

    /**
     * @Name: deleteUserByGid
     * @Description: 删除整个公会的用户
     * @Param: gid qq群号
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 15:45
     **/
    int deleteUserByGid(@Param("gid") Long gid);

    /**
     * @Name: updateUser
     * @Description: 修改用户
     * @Param: user
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 15:33
     **/
    int updateUser(User user);

    /**
     * @Name: resetUserState
     * @Description: 重置用户出刀状态时 用
     * @Param: gid
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/23 12:18
     **/
    int updateUserByGid(User user);


    /**
     * @Name: findUser
     * @Description: 查询用户 精确查询到某一工会某一人
     * @Param: uid
     * @Return: com.whitemagic2014.pojo.pcr.User
     * @Author: magic chen
     * @Date: 2020/8/22 15:32
     **/
    User findUserByUid(@Param("gid") Long gid, @Param("uid") Long uid);

    /**
     * @Name: findUserByGid
     * @Description: 查询公会当前所有成员
     * @Param: uid
     * @Return: java.util.List<com.whitemagic2014.pojo.pcr.User>
     * @Author: magic chen
     * @Date: 2020/8/22 22:14
     **/
    List<User> findUserByGid(@Param("gid") Long gid);


    /**
     * @Name: 创建公会
     * @Description:
     * @Param: guild
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 15:18
     **/
    int addGuile(Guild guild);


    /**
     * @Name: deleteGuile
     * @Description: 删除公会
     * 注意 删除公会 的时候要清理相关数据 用户，boss（出刀记录）
     * @Param: gid
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 15:50
     **/
    int deleteGuile(@Param("gid") Long gid);

    /**
     * @Name: findGuildByGid
     * @Description: 查询公会
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.pcr.Guild
     * @Author: magic chen
     * @Date: 2020/8/22 15:55
     **/
    Guild findGuildByGid(@Param("gid") Long gid);


    /**
     * @Name: addBoss
     * @Description: 创建boss
     * @Param: boss
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 15:58
     **/
    int addBoss(Boss boss);

    /**
     * @Name: deleteBossById
     * @Description: 用于删除单个boss（撤销尾刀的时候需要用到）
     * @Param: id boss的自增id
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 16:01
     **/
    int deleteBossById(@Param("id") Integer id);

    /**
     * @Name: deleteBossByGid
     * @Description: 删除一个公会下所有boss 清除工会战数据，解散公会的时候用到
     * @Param: gid
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 16:02
     **/
    int deleteBossByGid(@Param("gid") Long gid);

    /**
     * @Name: deleteBossAfterNow
     * @Description: 删除指定的boss，以及之后所有的boss
     * 用于直接修改boss状态的时候，如果不删除后面的boss，会在撤销之后的尾刀时出错
     * @Param: active
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/28 17:05
     **/
    int deleteBossAfterNow(Boss define);


    /**
     * @Name: updateBoss
     * @Description: 变更boss状态
     * @Param: boss
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 16:04
     **/
    int updateBoss(Boss boss);


    /**
     * @Name: selectBossByGid
     * @Description: 找到某公会当前boss
     * @Param:
     * @Return: com.whitemagic2014.pojo.pcr.Boss
     * @Author: magic chen
     * @Date: 2020/8/22 16:06
     **/
    Boss findBossActiveByGid(@Param("gid") Long gid);

    /**
     * @Name: findBossDefineByGid
     * @Description: 找到某公会指定周目 第几个boss
     * @Param: gid
     * @Param: cycle
     * @Param: num
     * @Return: com.whitemagic2014.pojo.pcr.Boss
     * @Author: magic chen
     * @Date: 2020/8/22 16:09
     **/
    Boss findBossDefineByGid(@Param("gid") Long gid, @Param("cycle") Integer cycle, @Param("num") Integer num);


    /**
     * @Name: addBattle
     * @Description: 新增出刀记录
     * @Param: battle
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 16:47
     **/
    int addBattle(Battle battle);


    /**
     * @Name: deleteBattle
     * @Description: 删除刀（撤销用）
     * @Param: time
     * @Param: gid
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 16:52
     **/
    int deleteBattle(@Param("id") Long id);

    /**
     * @Name: deleteBattleByGid
     * @Description: 删除某公会所有刀 解散
     * @Param: id
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 20:48
     **/
    int deleteBattleByGid(@Param("gid") Long gid);

    /**
     * @Name: findLastBattle
     * @Description: 找到公会 某人 最后一刀出刀记录
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.pcr.Battle
     * @Author: magic chen
     * @Date: 2020/8/22 18:50
     **/
    Battle findLastBattleSelf(@Param("gid") Long gid, @Param("uid") Long uid);


    /**
     * @Name: findLastBattleGuild
     * @Description: 找到全公会最后一刀
     * @Param: gid
     * @Return: com.whitemagic2014.pojo.pcr.Battle
     * @Author: magic chen
     * @Date: 2020/8/24 17:01
     **/
    Battle findLastBattleGuild(@Param("gid") Long gid);

    /**
     * @Name: findBattleByConditions
     * @Description: 查刀
     * @Param: battle
     * @Return: java.util.List<com.whitemagic2014.pojo.pcr.Battle>
     * @Author: magic chen
     * @Date: 2020/8/22 16:55
     **/
    List<Battle> findBattleByConditions(Battle battle);

    /**
     * @Name: checkKnifeNum
     * @Description: 注意要查询的刀 type != BattleType.extra 补偿刀不计数
     * @Param: gid
     * @Param: uid
     * @Param: time
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/24 16:47
     **/
    int checkKnifeNum(@Param("gid") Long gid, @Param("uid") Long uid, @Param("time") String time);

    /**
     * @Name: addNotice
     * @Description: 创建通知
     * @Param: notice
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 17:59
     **/
    int addNotice(Notice notice);

    /**
     * @Name: deleteNotice
     * @Description: 动态条件组合 删除通知
     * @Param: notice
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/22 18:00
     **/
    int deleteNotice(Notice notice);

    /**
     * @Name: findNoticeByConditions
     * @Description: 查询通知
     * @Param: notice
     * @Return: java.util.List<com.whitemagic2014.pojo.pcr.Notice>
     * @Author: magic chen
     * @Date: 2020/8/22 18:00
     **/
    List<Notice> findNoticeByConditions(Notice notice);

}
