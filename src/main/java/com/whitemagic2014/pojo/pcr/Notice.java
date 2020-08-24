package com.whitemagic2014.pojo.pcr;

import com.whitemagic2014.dic.PcrNoticeType;

/**
 * @Description: 通知 预约boss 和 挂树通知
 * @author: magic chen
 * @date: 2020/8/22 17:43
 **/
public class Notice {

    // 群号
    Long gid;

    // 用户qq
    Long uid;

    // 预约的是几王 1-5
    Integer bossNum;

    // 需要通知类型 挂树是这个boss死了通知 ，预约是这个boss出现通知
    PcrNoticeType type;

    public Long getGid() {
        return gid;
    }

    public Long getUid() {
        return uid;
    }

    public Integer getBossNum() {
        return bossNum;
    }

    public PcrNoticeType getType() {
        return type;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setBossNum(Integer bossNum) {
        this.bossNum = bossNum;
    }

    public void setType(PcrNoticeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "gid=" + gid +
                ", uid=" + uid +
                ", bossNum=" + bossNum +
                ", type=" + type +
                '}';
    }
}
