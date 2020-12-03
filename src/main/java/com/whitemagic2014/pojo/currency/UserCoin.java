package com.whitemagic2014.pojo.currency;

/**
 * @Description: 用户货币对象
 * @author: magic chen
 * @date: 2020/12/2 16:47
 **/
public class UserCoin {

    // 用户qq号
    Long uid;

    // 用户货币 魔力币
    Long magicCoin;

    // 开户时间 yyyy-MM-dd HH:mm:ss
    String time;

    // 账户是否可用
    Boolean available;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getMagicCoin() {
        return magicCoin;
    }

    public void setMagicCoin(Long magicCoin) {
        this.magicCoin = magicCoin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
