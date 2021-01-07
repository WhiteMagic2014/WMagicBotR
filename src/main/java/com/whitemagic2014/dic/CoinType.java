package com.whitemagic2014.dic;

/**
 * @Description: 货币类型
 * @author: magic chen
 * @date: 2020/12/2 16:56
 **/
public enum CoinType {

    // 注意货币类型 取名必须要和 UserCoin 中的一样 否则反射取值可能会有问题
    magicCoin("魔力币");

    String desc;

    CoinType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
