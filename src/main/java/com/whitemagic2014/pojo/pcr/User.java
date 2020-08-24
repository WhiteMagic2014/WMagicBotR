package com.whitemagic2014.pojo.pcr;

import com.whitemagic2014.dic.UserRole;
import com.whitemagic2014.util.DateFormatUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    // 状态有效期
    String stateTime;

    // 剩余刀 每日 3
    Integer knife;

    // 当天是否sl 每日一次
    Boolean sl;


    public UserRole getRole() {
        return role;
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

    public String getStateTime() {
        return stateTime;
    }

    public Integer getKnife() {
        return knife;
    }

    public Boolean getSl() {
        return sl;
    }


    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public void setStateTime(String stateTime) {
        this.stateTime = stateTime;
    }

    public void setKnife(Integer knife) {
        this.knife = knife;
    }

    public void setSl(Boolean sl) {
        this.sl = sl;
    }


    /**
     * @Name: refresh
     * @Description: 每次获得状态前请务必 调用 refresh 检查是否隔日刷新
     * @Param:
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/8/22 12:43
     **/
    public void refresh() {
        try {
            // 该用户状态创建日0点 比如 8月22日下午3点创建,被认为是 8月22日 0:00 创建
            Date createStart = DateFormatUtil.getDayStart(DateFormatUtil.sdf.parse(stateTime));
            // 这个状态过期时间 国服游戏刷新时间为第二天5点 所以是 24+5小时
            Date expireTime = DateFormatUtil.dateAdd(createStart, (24L + 5L), TimeUnit.HOURS);
            //超过了过期时间 需要重置
            if (new Date().after(expireTime)) {
                this.stateTime = DateFormatUtil.sdf.format(new Date());
                this.knife = 3;
                this.sl = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

