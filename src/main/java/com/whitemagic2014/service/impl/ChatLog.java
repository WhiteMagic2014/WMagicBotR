package com.whitemagic2014.service.impl;

public class ChatLog {

    private String user;
    private String assistant;

    public ChatLog(String user, String assistant) {
        this.user = user;
        this.assistant = assistant;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }
}
