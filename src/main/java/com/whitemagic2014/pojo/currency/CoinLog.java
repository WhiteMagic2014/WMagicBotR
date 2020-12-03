package com.whitemagic2014.pojo.currency;

import com.whitemagic2014.dic.CoinType;

/**
 * @Description: 货币变更记录
 * @author: magic chen
 * @date: 2020/12/2 16:53
 **/
public class CoinLog {

    // 自增流水号
    Long id;
    // 用户id
    Long uid;
    // 在哪一个群发生的变更 如果不写则说明是管理员充值
    Long gid;
    // 货币类型
    CoinType type;
    // 变更金额
    Long amount;
    // 变更前金额
    Long before;
    // 变更后金额
    Long after;
    // 具体内容描述
    String remark;
    // log记录时间  yyyy-MM-dd HH:mm:ss
    String time;
    // 备用字段1
    String field1;
    // 备用字段2
    String field2;
    // 备用字段3
    String field3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public CoinType getType() {
        return type;
    }

    public void setType(CoinType type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBefore() {
        return before;
    }

    public void setBefore(Long before) {
        this.before = before;
    }

    public Long getAfter() {
        return after;
    }

    public void setAfter(Long after) {
        this.after = after;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    @Override
    public String toString() {
        return "CoinLog{" +
                "id=" + id +
                ", uid=" + uid +
                ", gid=" + gid +
                ", type=" + type +
                ", amount=" + amount +
                ", before=" + before +
                ", after=" + after +
                ", remark='" + remark + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
