package com.whitemagic2014.pojo.pcr;

import com.whitemagic2014.dic.UserRole;

/**
 * @Description: 工会成员 && 用户当天出刀状态 用于出刀时的逻辑判断
 * 报刀时 knife - 1 ，为0之后无法出刀
 * 报尾刀成功时 knife不减
 * @Waring: 每次获得状态前请务必 调用 refresh 检查是否隔日刷新
 * @author: magic chen
 * @date: 2020/8/22 12:50
 **/
public class User {

    // 用户qq号
    Long uid;

    // 用户名（群昵称）
    String uname;

    // 所属公会
    Long gid;

    // 权限 和群里的权限无关 可能不是群管理员，但是可以是 公会战的管理员
    UserRole role;

    // 当天是否sl 每日一次
    Boolean sl;

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setSl(Boolean sl) {
        this.sl = sl;
    }

    public Long getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public Long getGid() {
        return gid;
    }

    public UserRole getRole() {
        return role;
    }

    public Boolean getSl() {
        return sl;
    }
}

