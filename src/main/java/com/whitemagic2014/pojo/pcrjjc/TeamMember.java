package com.whitemagic2014.pojo.pcrjjc;

/**
 * @Description: jjc 队员
 * @author: magic chen
 * @date: 2020/8/25 17:17
 **/
public class TeamMember {

    //角色id
    Integer id;

    //星数
    Integer star;

    //专武
    Boolean equip;

    // 角色名
    String name;

    public Integer getId() {
        return id;
    }

    public Integer getStar() {
        return star;
    }

    public Boolean getEquip() {
        return equip;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public void setEquip(Boolean equip) {
        this.equip = equip;
    }

    public void setName(String name) {
        this.name = name;
    }
}
