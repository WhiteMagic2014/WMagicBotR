package com.whitemagic2014.service;

import java.util.List;

/**
 * @Description: chat pgt
 * @author: magic chen
 * @date: 2023/2/10 15:26
 **/
public interface ChatPGTService {

    // 作n副图
    List<String> image(String prompt, int n);

    // 问答
    String chat(String prompt);

}
