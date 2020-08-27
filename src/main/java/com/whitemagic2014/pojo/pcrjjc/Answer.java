package com.whitemagic2014.pojo.pcrjjc;

import java.util.Date;
import java.util.List;

/**
 * @Description: jjc解法
 * @author: magic chen
 * @date: 2020/8/25 17:13
 **/
public class Answer {

    String id;

    List<TeamMember> atk;

    List<TeamMember> def;

    Integer up;

    Integer down;

    Date updated;

    public String getId() {
        return id;
    }

    public List<TeamMember> getAtk() {

        return atk;
    }

    public List<TeamMember> getDef() {
        return def;
    }

    public Integer getUp() {
        return up;
    }

    public Integer getDown() {
        return down;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAtk(List<TeamMember> atk) {
        this.atk = atk;
    }

    public void setDef(List<TeamMember> def) {
        this.def = def;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public void setDown(Integer down) {
        this.down = down;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
