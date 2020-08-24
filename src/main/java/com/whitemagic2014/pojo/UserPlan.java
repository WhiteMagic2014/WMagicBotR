package com.whitemagic2014.pojo;

/**
 * @Description: 用户计划表
 * @author: magic chen
 * @date: 2020/8/21 17:18
 **/
public class UserPlan {

    int id;

    String uid;

    int itemId;

    String itemName;

    int nowNum;

    int planNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getNowNum() {
        return nowNum;
    }

    public void setNowNum(int nowNum) {
        this.nowNum = nowNum;
    }

    public int getPlanNum() {
        return planNum;
    }

    public void setPlanNum(int planNum) {
        this.planNum = planNum;
    }
}
