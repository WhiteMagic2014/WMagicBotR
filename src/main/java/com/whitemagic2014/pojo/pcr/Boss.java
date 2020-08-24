package com.whitemagic2014.pojo.pcr;

/**
 * @Description: pcr 公会战boss
 * @author: magic chen
 * @date: 2020/8/22 11:45
 **/
public class Boss {

    // 一个自增id
    Integer id;

    // 群号
    Long gid;

    // 是几周目boss 1~n
    Integer cycle;

    // 这个boss是老几 1~5
    Integer num;

    // 总血量
    Long hp;

    // 当前血量
    Long hpnow;

    //是否当前boss
    Boolean active;


    public void setId(Integer id) {
        this.id = id;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setHp(Long hp) {
        this.hp = hp;
    }

    public void setHpnow(Long hpnow) {
        this.hpnow = hpnow;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public Long getGid() {
        return gid;
    }

    public Integer getCycle() {
        return cycle;
    }

    public Integer getNum() {
        return num;
    }

    public Long getHp() {
        return hp;
    }

    public Long getHpnow() {
        return hpnow;
    }

    public Boolean getActive() {
        return active;
    }
}
