package com.whitemagic2014.vo.pcr;

import com.whitemagic2014.dic.BattleType;

/**
 * @Description: 页面查刀用vo
 * @author: magic chen
 * @date: 2020/10/26 15:55
 **/
public class BattleVo {

    // 出刀id 自增 ,撤销的时候删除最上面一条
    Long id;

    // 是几周目boss 1~n
    Integer cycle;

    // 这个boss是老几 1~5
    Integer num;

    // 这一刀对应的boss
    Integer bossid;

    // 用户id
    Long uid;

    // 用户名
    String uname;

    // 所属公会
    Long gid;

    // 出刀伤害
    Long damage;

    //是否击杀boss
    Boolean killboss;

    // 出刀类型 尾刀end/补偿刀extra/正常刀nomal
    BattleType type;

    // 此刀属于那一天 yyyy-dd-mm
    String time;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getBossid() {
        return bossid;
    }

    public void setBossid(Integer bossid) {
        this.bossid = bossid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getDamage() {
        return damage;
    }

    public void setDamage(Long damage) {
        this.damage = damage;
    }

    public Boolean getKillboss() {
        return killboss;
    }

    public void setKillboss(Boolean killboss) {
        this.killboss = killboss;
    }

    public BattleType getType() {
        return type;
    }

    public void setType(BattleType type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BattleVo{" +
                "id=" + id +
                ", cycle=" + cycle +
                ", num=" + num +
                ", bossid=" + bossid +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                ", gid=" + gid +
                ", damage=" + damage +
                ", killboss=" + killboss +
                ", type=" + type +
                ", time='" + time + '\'' +
                '}';
    }
}
