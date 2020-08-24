package com.whitemagic2014.pojo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description: 指令名称
 * @author: magic chen
 * @date: 2020/8/20 22:30
 **/
public class CommandProperties {


    public String name;

    public ArrayList<String> alias;


    public String getName() {
        return name;
    }

    public ArrayList<String> getAlias() {
        return alias;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(ArrayList<String> alias) {
        this.alias = alias;
    }


    /**
     * 指令属性构造器
     *
     * @param name  指令名
     * @param alias 其他指令名
     */
    public CommandProperties(String name, ArrayList<String> alias) {
        this.name = name;
        this.alias = alias;
    }

    /**
     * 指令属性构造器封装
     *
     * @param name 指令名
     */
    public CommandProperties(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * 指令属性构造器封装
     *
     * @param name  指令名
     * @param alias 其他指令名
     */
    public CommandProperties(String name, String... alias) {
        this(name, new ArrayList<>(Arrays.asList(alias)));
    }

    /**
     * 构造CommandProperty
     *
     * @param name 指令名称
     */
    public static CommandProperties name(String name) {
        return new CommandProperties(name);
    }

    /**
     * 添加指令别名
     *
     * @param alias 指令别名
     */
    public CommandProperties alias(String alias) {
        this.alias.add(alias);
        return this;
    }

}
