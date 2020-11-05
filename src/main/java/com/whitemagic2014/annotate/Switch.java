package com.whitemagic2014.annotate;

import java.lang.annotation.*;

/**
 * @Description: 开关注解
 * @author: magic chen
 * @date: 2020/9/23 18:08
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Switch {

    // 开关名
    String name();

    // 组件默认是否开启
    boolean defaultOn() default false;

}
