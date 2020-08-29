package com.whitemagic2014.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 自用的一些工具类
 * @author: magic chen
 * @date: 2020/8/22 12:29
 **/
public class MagicHelper {


    private static DecimalFormat df1 = new DecimalFormat("#,###");

    private static Random random = new Random();

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


    /**
     * @Name: pcrToday
     * @Description: 获得pcr时制的 今天
     * @Param:
     * @Return: java.util.Date
     * @Author: magic chen
     * @Date: 2020/8/24 16:23
     **/
    private static Date pcrTodayInternal() {
        // 现在时间 比如 2020-08-24 16:18:12
        Date now = new Date();
        // 今天0点 2020-08-24 00:00:00
        Date today0h = DateFormatUtil.getDayStart(now);
        // 今天5点  2020-08-24 05:00:00
        Date today5h = DateFormatUtil.dateAdd(today0h, 5L, TimeUnit.HOURS);

        if (now.after(today5h)) {
            // 现在时间过了 5点了 才真正算新的一天
            return today0h;
        } else {
            // 否则算前一天
            return DateFormatUtil.dateMinus(today0h, 1L, TimeUnit.DAYS);
        }
    }

    /**
     * @Name: pcrToday
     * @Description: 获得pcr时间制度的今天
     * @Param:
     * @Return: yyyy-MM-dd
     * @Author: magic chen
     * @Date: 2020/8/24 16:26
     **/
    public static String pcrToday() {
        return DateFormatUtil.sdfv2.format(pcrTodayInternal());
    }

    /**
     * @Name: pcrYesterday
     * @Description: 获得pcr时间制度的昨天
     * @Param:
     * @Return: yyyy-MM-dd
     * @Author: magic chen
     * @Date: 2020/8/24 16:26
     **/
    public static String pcrYesterday() {
        Date yes = DateFormatUtil.dateMinus(pcrTodayInternal(), 1L, TimeUnit.DAYS);
        return DateFormatUtil.sdfv2.format(yes);
    }

    /**
     * @Name: randomInt
     * @Description: 获得一个随机数
     * @Param: max  随机数最大值
     * @Return: [0, max)
     * @Author: magic chen
     * @Date: 2020/8/27 10:42
     **/
    public static Integer randomInt(Integer max) {
        if (max == null) {
            return random.nextInt();
        } else {
            return random.nextInt(max);
        }
    }


    /**
     * @Name: resizeBufferedImage
     * @Description: 缩放 BufferedImage
     * @Param: source
     * @Param: targetW
     * @Param: targetH
     * @Param: flag 是否等比缩放
     * @Return: java.awt.image.BufferedImage
     * @Author: magic chen
     * @Date: 2020/8/29 13:14
     **/
    public static BufferedImage resizeBufferedImage(BufferedImage source, int targetW, int targetH, boolean flag) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (flag && sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else if (flag && sx <= sy) {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }


}
