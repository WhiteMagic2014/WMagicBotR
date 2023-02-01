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
}
