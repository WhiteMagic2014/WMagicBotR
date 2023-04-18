package com.whitemagic2014.service.impl;

/**
 * @Description: 使用OriginChat 所需的参数
 * @author: magic chen
 * @date: 2023/4/17 15:08
 **/
public class OriginChatVO {

    String role;//system,user,assistant

    String prompt;

    public OriginChatVO(String role, String prompt) {
        this.role = role;
        this.prompt = prompt;
    }

    public OriginChatVO() {
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

}
