package com.whitemagic2014.util;

/**
 * @Description: 路径工具
 * @author: magic chen
 * @date: 2020/8/20 15:07
 **/
public class Path {


    /**
     * @Name: getPath
     * @Description: 获得当前运行环境path
     * @Param:
     * @Return: java.lang.String
     * @Author: magic chen
     * @Date: 2020/8/20 15:09
     **/
    public static String getPath() {
        String path = Path.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (System.getProperty("os.name").contains("dows")) {
            path = path.substring(1, path.length());
        }
        if (path.contains("jar")) {
            path = path.substring(0, path.lastIndexOf("."));
            path = path.substring(0, path.lastIndexOf("/") + 1);
        }
        path = path.replace("target/classes/", "").replace("file:", "");
        return path;

    }

}
