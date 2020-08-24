package com.whitemagic2014.dic;

/**
 * @Description: 出刀类型
 * @author: magic chen
 * @date: 2020/8/22 14:13
 **/
public enum BattleType {

    end("尾刀"), extra("补偿刀"), nomal("正常刀");

    String desc;

    BattleType(String desc) {
        this.desc = desc;
    }


    public boolean isEnd() {
        return this.equals(end);
    }

    public boolean isNotEnd() {
        return !this.equals(end);
    }

}
