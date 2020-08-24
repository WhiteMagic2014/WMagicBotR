package com.whitemagic2014.pojo;

/**
 * @Description: 私人特供
 * @author: magic chen
 * @date: 2020/8/21 18:25
 **/
public class CanEat {
    Integer id;
    String itemName;
    Boolean can;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getCan() {
        return can;
    }

    public void setCan(Boolean can) {
        this.can = can;
    }
}
