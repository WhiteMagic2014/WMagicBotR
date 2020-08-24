package com.whitemagic2014.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 自用的一些工具类
 * @author: magic chen
 * @date: 2020/8/22 12:29
 **/
public class MagicHelper {

    private static DecimalFormat df1 = new DecimalFormat("#,###");

    /**
     * @Name: longAddComma
     * @Description: 给 long类型数字 加上逗号格式化
     * @Param: longNum
     * @Return: java.lang.String
     * @Author: magic chen
     * @Date: 2020/8/23 00:36
     **/
    public static String longAddComma(Long longNum) {
        return df1.format(longNum);
    }


}
