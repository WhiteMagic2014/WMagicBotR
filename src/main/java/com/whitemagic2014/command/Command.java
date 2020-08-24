package com.whitemagic2014.command;

import com.whitemagic2014.pojo.CommandProperties;

/**
 * @Description: 指令接口 当接收到 指令(properties) 执行动作
 * @author: magic chen
 * @date: 2020/8/20 21:40
 **/
public interface Command {

    /**
     * @Name: properties
     * @Description: 构造指令集合
     * @Param:
     * @Return: com.whitemagic2014.pojo.CommandProperties
     * @Author: magic chen
     * @Date: 2020/8/20 22:35
     **/
    CommandProperties properties();

}
