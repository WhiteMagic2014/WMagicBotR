package com.whitemagic2014.annotate;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Description: gpt函数调用 专用注解 自动注册进 chat
 * @author: magic chen
 * @date: 2023/12/1 15:55
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface Function {

    /**
     * @Name: autoLoad
     * @Description: 默认自动注入
     * @Param:
     * @Return: boolean
     * @Author: magic chen
     * @Date: 2020/11/5 10:46
     **/
    boolean autoLoad() default true;

}
