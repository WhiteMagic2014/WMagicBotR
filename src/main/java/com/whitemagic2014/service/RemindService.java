package com.whitemagic2014.service;

import java.util.Date;

/**
 * @Description: 备忘功能service
 * @author: magic chen
 * @date: 2023/4/13 14:55
 **/
public interface RemindService {


    /**
     * 读取之前的备忘记录
     */
    void loadTask();

    /**
     * 群聊备忘
     *
     * @param gid    群id
     * @param atId   需要@的成员
     * @param msgStr 备忘内容
     * @param date   提醒时间
     * @return 备忘taskKey
     */
    String groupRemind(Long gid, Long atId, String msgStr, Date date);


    /**
     * 私聊备忘
     *
     * @param uid    好友id
     * @param msgStr 备忘内容
     * @param date   提醒时间
     * @return 备忘taskKey
     */
    String friendRemind(Long uid, String msgStr, Date date);

    /**
     * 取消备忘
     *
     * @param taskKey
     * @return
     */
    String groupCancelRemind(String taskKey);

}
