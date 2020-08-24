package com.whitemagic2014.pojo.pcr;

/**
 * @Description: 锁定boss 锁定只维持3分钟
 * @author: magic chen
 * @date: 2020/8/23 10:08
 **/
public class BossLock {

    public static final String getLockname(Long gid) {
        return "BossLock" + gid;
    }

    //锁定类型为 申请出刀
    public static final String request = "request";

    public BossLock(Long uid, String uname, String desc) {
        this.uid = uid;
        this.desc = desc;
        this.uname = uname;
    }

    public BossLock() {
    }

    // 锁定人
    Long uid;

    // 锁定类型 申请出刀会在出刀后自动解锁
    String desc;

    // 锁定用户名
    String uname;

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getUid() {
        return uid;
    }

    public String getDesc() {
        return desc;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUname() {
        return uname;
    }
}
