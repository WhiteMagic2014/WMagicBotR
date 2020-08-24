package com.whitemagic2014.pojo.pcr;


import com.whitemagic2014.dic.BattleType;

/**
 * @Description: 出刀
 * @author: magic chen
 * @date: 2020/8/22 11:56
 **/
public class Battle {

    // 出刀id 自增 ,撤销的时候删除最上面一条
    Long id;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setBossid(Integer bossid) {
        this.bossid = bossid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public void setDamage(Long damage) {
        this.damage = damage;
    }

    public void setType(BattleType type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public Integer getBossid() {
        return bossid;
    }

    public Long getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public Long getGid() {
        return gid;
    }

    public Long getDamage() {
        return damage;
    }

    public BattleType getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public void setKillboss(Boolean killboss) {
        this.killboss = killboss;
    }

    public Boolean getKillboss() {
        return killboss;
    }

    @Override
    public String toString() {
        return "Battle{" +
                "id=" + id +
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
