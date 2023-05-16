package com.whitemagic2014.service;

import com.whitemagic2014.service.impl.OriginChatVO;

import java.util.List;

/**
 * @Description: chat pgt
 * @author: magic chen
 * @date: 2023/2/10 15:26
 **/
public interface ChatPGTService {

    // 作n副图
    List<String> image(String prompt, int n);

    /**
     * 交谈
     *
     * @param session 对话session
     * @param prompt  对话
     * @return
     */
    String chat(String session, String prompt);

    /**
     * 性格设定
     *
     * @param session 对话session
     * @param setting 性格设定
     * @return
     */
    String setPersonality(String session, String setting);


    /**
     * 清除 session 上下文
     *
     * @param session
     * @return
     */
    String clearLog(String session);


    /**
     * 调用基础的chat接口
     *
     * @param voList
     * @return
     */
    String originChat(List<OriginChatVO> voList);


    /**
     * 输入转向量
     *
     * @param inputs
     * @return
     */
    List<List<Double>> input2Vector(List<String> inputs);

    /**
     * 根据训练的数据向量 回答问题
     *
     * @param question
     * @return
     */
    String answer(String question);

    /**
     * 重新读取数据向量集合
     *
     * @return
     */
    String reloadVector();

}
