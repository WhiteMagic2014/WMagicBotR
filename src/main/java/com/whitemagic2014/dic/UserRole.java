package com.whitemagic2014.dic;

/**
 * @Description: 用户权限 和 群里的权限不一样 组合业务判断
 * @author: magic chen
 * @date: 2020/8/22 14:17
 **/
public enum UserRole {
    OWNER("最高权限 机器人主人 唯一"), // 在配置文件中配置的唯一管理员
    ADMIN("管理员"), //(群主和群管理在这里都作为管理员)
    MEMBER("一般成员");

    String desc;

    UserRole(String desc) {
        this.desc = desc;
    }

    public boolean isAdmin() {
        if (this == OWNER || this == ADMIN) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOwner() {
        if (this == OWNER) {
            return true;
        } else {
            return false;
        }
    }

}
