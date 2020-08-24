package com.whitemagic2014.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 便捷时间格式化处理
 * @author: magic chen
 * @date: 2020/8/20 15:03
 **/
public class DateFormatUtil {


    private static final Logger logger = LoggerFactory.getLogger(DateFormatUtil.class);


    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat sdfv2 = new SimpleDateFormat("yyyy-MM-dd");


    /*
     * @Name: getDayStart
     * @Description: 获得传入那天的 开始
     * @Param: date
     * @Return: java.util.Date
     *
     * @Author: magic chen
     * @Date:   2020/8/22 12:37
     **/
    public static Date getDayStart(Date date) {
        String timeStr = sdfv2.format(date);
        Date start = null;
        try {
            start = sdf.parse(timeStr + " 00:00:00");
        } catch (ParseException e) {
            logger.error("error:", e);
        }
        return start;
    }


    /**
     * @Name: getDayEnd
     * @Description: 获得传入那天的 结束
     * @Param: date
     * @Return: java.util.Date
     * @Author: magic chen
     * @Date: 2020/8/22 12:37
     **/
    public static Date getDayEnd(Date date) {
        String timeStr = sdfv2.format(date);
        Date end = null;
        try {
            end = sdf.parse(timeStr + " 23:59:59");
        } catch (ParseException e) {
            logger.error("error:", e);
        }
        return end;
    }


    /**
     * @Name: getYearStart
     * @Description: 获得传入年份的开始date
     * @Param: year 如2018
     * @Return: java.util.Date
     * @Author: magic chen
     * @Date: 2020/8/22 12:37
     **/
    public static Date getYearStart(String year) {
        Date date = null;
        try {
            date = sdf.parse(year + "-01-01 00:00:00");
        } catch (ParseException e) {
            logger.error("error:", e);
        }
        return date;
    }


    /**
     * @Name: getYearEnd
     * @Description: 获得传入年份的结束date
     * @Param: year 如2018
     * @Return: java.util.Date
     * @Author: magic chen
     * @Date: 2020/8/22 12:36
     **/
    public static Date getYearEnd(String year) {
        Date date = null;
        try {
            date = sdf.parse(year + "-12-31 23:59:59");
        } catch (ParseException e) {
            logger.error("error:", e);
        }
        return date;
    }


    /**
     * @Name: dateMinus
     * @Description: 日期减少
     * @Param: date
     * @Param: num
     * @Param: size
     * @Return: java.util.Date
     * @Author: magic chen
     * @Date: 2020/8/22 12:36
     **/
    public static Date dateMinus(Date date, Long num, TimeUnit size) {
        return dateAdd(date, num * -1, size);
    }

    /**
     * @Name: dateAdd
     * @Description: 日期增加
     * @Param: date 初始日期
     * @Param: num 变化值
     * @Param: size 变化值
     * @Return: java.util.Date
     * @Author: magic chen
     * @Date: 2020/8/22 12:35
     **/
    public static Date dateAdd(Date date, Long num, TimeUnit size) {
        switch (size) {
            case MILLISECONDS:
                break;

            case SECONDS:
                num = num * 1000;
                break;

            case MINUTES:
                num = num * 1000 * 60;
                break;

            case HOURS:
                num = num * 1000 * 60 * 60;
                break;

            case DAYS:
                num = num * 1000 * 60 * 60 * 24;
                break;

            default:
                return date;
        }
        return new Date(date.getTime() + num);
    }


    /**
     * @Name: getBetweenDate
     * @Description: 给定 头尾日期，获得期间每一天日期 （支持年月日）
     * @Param: fromDate 2019-12-31 xx:xx:xx
     * @Param: toDate 2020-01-02 xx:xx:xx
     * @Param: type
     * @Return: 年返回（2019,2020） 月返回（2019-12,2020-01） 日返回（2019-12-31,2020-01-01,2020-01-02）
     * @Author: magic chen
     * @Date: 2020/8/22 12:35
     **/
    public static List<String> getBetweenDate(Date fromDate, Date toDate, int type) {
        SimpleDateFormat sdf = null;
        if (type == Calendar.YEAR) {
            sdf = new SimpleDateFormat("yyyy");
        } else if (type == Calendar.MONTH) {
            sdf = new SimpleDateFormat("yyyy-MM");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        List<String> result = new ArrayList<String>();
        // 得到最后一个日期
        String endStr = sdf.format(toDate);
        // 设置初始时间
        Calendar temp = Calendar.getInstance();
        temp.setTime(fromDate);
        while (true) {
            String tempStr = sdf.format(temp.getTime());
            result.add(tempStr);
            // 判断刚刚添加 的 是否已经为最后一个日期
            if (tempStr.equals(endStr)) {
                break;
            }
            temp.add(type, 1);
        }
        return result;
    }


}
