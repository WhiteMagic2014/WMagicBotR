package com.whitemagic2014.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * @Description: 处理image
 * @author: magic chen
 * @date: 2020/9/16 11:45
 **/
public class MagicImage {


    /*
     * @Name: cut
     * @Description: 切割图片
     * @Param: x
     * @Param: y
     * @Param: wight
     * @Param: hight
     * @Param: img
     * @Return: java.awt.image.BufferedImage
     *
     * @Author: magic chen
     * @Date:   2020/9/16 14:25
     **/
    public static BufferedImage cut(int x, int y, int wight, int hight, BufferedImage img) {
        int[] simgRgb = new int[wight * hight];
        img.getRGB(x, y, wight, hight, simgRgb, 0, wight);
        BufferedImage newImage = new BufferedImage(wight, hight, BufferedImage.TYPE_INT_ARGB);
        newImage.setRGB(0, 0, wight, hight, simgRgb, 0, wight);
        return newImage;
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
