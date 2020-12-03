package com.whitemagic2014.dic;

/**
 * @Description: 货币类型
 * @author: magic chen
 * @date: 2020/12/2 16:56
 **/
public enum CoinType {

    magicCoin("魔力币");

    String desc;

    CoinType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
