package com.whitemagic2014.pojo;

/**
 * @Description: 火纹engage 续战
 * @author: magic chen
 * @date: 2023/1/29 11:54
 **/
public class EngageBattle {

    // 自增id
    Integer id;

    // 续战码
    String battleKey;

    // 状态  0=已结束，1=可用，2=使用中
    Integer status;

    // 备注
    String remark;

    // 房主 qq号
    String qqNum;

    // 参与人 qq号 以认领为准
    String linkNum1;
    String linkNum2;
    String linkNum3;
    String linkNum4;
    // 结束续战的人
    String finNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBattleKey() {
        return battleKey;
    }

    public void setBattleKey(String battleKey) {
        this.battleKey = battleKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public String getLinkNum1() {
        return linkNum1;
    }

    public void setLinkNum1(String linkNum1) {
        this.linkNum1 = linkNum1;
    }

    public String getLinkNum2() {
        return linkNum2;
    }

    public void setLinkNum2(String linkNum2) {
        this.linkNum2 = linkNum2;
    }

    public String getLinkNum3() {
        return linkNum3;
    }

    public void setLinkNum3(String linkNum3) {
        this.linkNum3 = linkNum3;
    }

    public String getLinkNum4() {
        return linkNum4;
    }

    public void setLinkNum4(String linkNum4) {
        this.linkNum4 = linkNum4;
    }

    public String getFinNum() {
        return finNum;
    }

    public void setFinNum(String finNum) {
        this.finNum = finNum;
    }
}
