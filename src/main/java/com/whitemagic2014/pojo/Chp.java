package com.whitemagic2014.pojo;

/**
 * @Description: 彩虹屁
 * @author: magic chen
 * @date: 2022/7/18 11:15
 **/
public class Chp {

    String hash;
    String content;

    public Chp() {
    }

    public Chp(String hash, String content) {
        this.hash = hash;
        this.content = content;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
