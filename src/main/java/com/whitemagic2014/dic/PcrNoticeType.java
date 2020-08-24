package com.whitemagic2014.dic;

/**
 * @Description: pcr 通知类型
 * @author: magic chen
 * @date: 2020/8/22 17:44
 **/
public enum PcrNoticeType {

    tree("挂树"), order("预约");

    String desc;

    PcrNoticeType(String desc) {
        this.desc = desc;
    }
}
