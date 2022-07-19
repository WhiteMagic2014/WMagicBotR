package com.whitemagic2014.service;

/**
 * @Description: 夸夸service
 * @author: magic chen
 * @date: 2022/7/18 11:26
 **/
public interface FunnyTextService {


    /**
     * 获得彩虹屁
     *
     * @return
     */
    String getChp();

    /**
     * 获得毒鸡汤
     *
     * @return
     */
    String getDjt();

    /**
     * 获得朋友圈
     */
    String getPyq();

    /**
     * 注册夸夸 随机在用户发言后夸夸
     *
     * @param gid 群号
     * @param uid q号
     */
    void registChp(Long gid, Long uid);

    /**
     * 取消夸夸
     *
     * @param gid 群号
     * @param uid q号
     */
    void unRegistChp(Long gid, Long uid);

    /**
     * 检查是否需要夸夸
     *
     * @param gid
     * @param uid
     * @return
     */
    boolean checkRequireChp(Long gid, Long uid);

}
