package com.whitemagic2014.util;

import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.PrivateModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 开关控制器 用于动态开关某些功能
 * @author: magic chen
 * @date: 2020/8/27 17:34
 **/
public class MagicSwitch {

    private static final Map<String, Boolean> sw = new HashMap<>();

    public void init(String name, Boolean open) {
        sw.put(name, open);
    }

    /**
     * @Name: open
     * @Description: 开启某功能
     * @Param: name
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/27 17:43
     **/
    public PrivateModel<String> open(String name) {
        if (sw.containsKey(name)) {
            sw.put(name, true);
            return new PrivateModel<>(ReturnCode.SUCCESS, "success", "[" + name + "]已开启");
        } else {
            return new PrivateModel<>(ReturnCode.FAIL, "[" + name + "]功能不存在");
        }
    }

    /**
     * @Name: close
     * @Description: 关闭某功能
     * @Param: name
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/27 17:43
     **/
    public PrivateModel<String> close(String name) {
        if (sw.containsKey(name)) {
            sw.put(name, false);
            return new PrivateModel<>(ReturnCode.SUCCESS, "success", "[" + name + "]已关闭");
        } else {
            return new PrivateModel<>(ReturnCode.FAIL, "[" + name + "]功能不存在");
        }
    }

    /**
     * @Name: check
     * @Description: 检查某功能是否开启
     * @Param: name
     * @Return: boolean
     * @Author: magic chen
     * @Date: 2020/8/27 17:44
     **/
    public boolean check(String name) {
        return sw.getOrDefault(name, false);
    }

}
