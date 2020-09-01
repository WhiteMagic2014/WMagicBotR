package com.whitemagic2014.util;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 本项目不用外部redis，使用map模拟redis
 * @author: magic chen
 * @date: 2020/8/20 12:04
 **/
public class MagicMaps {


    // maxSize: 设置最大值,添加第11个entry时，会导致第1个立马过期(即使没到过期时间)
    // expiration：设置每个key有效时间10s, 如果key不设置过期时间，key永久有效。
    // variableExpiration: 允许更新过期时间值,如果不设置variableExpiration，不允许后面更改过期时间,一旦执行更改过期时间操作会抛异常UnsupportedOperationException
    // policy:
    //        CREATED: 只在put和replace方法清零过期时间
    //        ACCESSED: 在CREATED策略基础上增加, 在还没过期时get方法清零过期时间。
    //        清零过期时间也就是重置过期时间，重新计算过期时间.
    //    ExpiringMap<String, String> map = ExpiringMap.build
    private static final ExpiringMap<String, Object> map = ExpiringMap.builder()
//                   .maxSize(10)
//                   .expiration(10, TimeUnit.SECONDS)
            .variableExpiration().expirationPolicy(ExpirationPolicy.CREATED)
            .build();

    /**
     * @Name: put
     * @Description: 无过期时间放置
     * @Param: key
     * @Param: value
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/8/20 14:31
     **/
    public static void put(String key, Object value) {
        map.put(key, value);
    }

    /*
     * @Name: putWithExpire
     * @Description: 有过期时间放置
     * @Param: key
     * @Param: value
     * @Param: time
     * @Param: timeUnit
     * @Return: void
     *
     * @Author: magic chen
     * @Date:   2020/8/20 14:40
     **/
    public static void putWithExpire(String key, Object value, Long time, TimeUnit timeUnit) {
        map.put(key, value, time, timeUnit);
    }

    /*
     * @Name: get
     * @Description: 获取值,根据clazz序列化返回结果,如果key不存在返回null
     * @Param: key
     * @Param: clazz
     * @Return: T
     *
     * @Author: magic chen
     * @Date:   2020/8/20 14:49
     **/
    public static <T> T get(String key, Class<T> clazz) {
        if (map.containsKey(key)) {
            return (T) map.get(key);
        } else {
            return null;
        }
    }

    /**
     * @Name: getObject
     * @Description: 获取值, 直接返回object, 如果key不存在返回null
     * @Param: key
     * @Return: java.lang.Object
     * @Author: magic chen
     * @Date: 2020/8/20 15:01
     **/
    public static Object getObject(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return null;
        }
    }


    /*
     * @Name: check
     * @Description: 检查key是否存在
     * @Param: key
     * @Return: boolean
     *
     * @Author: magic chen
     * @Date:   2020/8/20 14:57
     **/
    public static boolean check(String key) {
        return map.containsKey(key);
    }


    /**
     * @Name: remove
     * @Description: 删除值
     * @Param: key
     * @Author: magic chen
     * @Date: 2020/8/23 10:13
     **/
    public static void remove(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
    }

}
