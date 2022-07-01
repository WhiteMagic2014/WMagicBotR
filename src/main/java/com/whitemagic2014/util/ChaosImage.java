package com.whitemagic2014.util;

import java.awt.image.BufferedImage;

/**
 * @Description: 混沌图片加密 (网络获取图片，经过压缩，效果不如本地文件那么好)
 * @author: magic chen
 * @date: 2022/7/1 10:53
 **/
public class ChaosImage {


    /**
     * @param image    源
     * @param password 密码8位数字
     * @param encode   true=加密，false=解密
     * @return
     */
    public static BufferedImage process(BufferedImage image, int password, boolean encode) {
        float x0_1, x0_2;
        //这里固定为4
        float u = 4f;

        password %= 100000000;
        x0_1 = (password % 10000) * 0.0001f;//1~4
        x0_2 = (password / 10000) * 0.0001f;//5~8

        int width = image.getWidth(),
                height = image.getHeight();
        int size = width * height;
        int[] rgb = new int[size];
        //读取像素从上到下，从左至右
        image.getRGB(0, 0, width, height, rgb, 0, width);

        float[] x = new float[size];
        //生成用于位置置换的混沌序列
        x[0] = x0_1;
        //迭代500次达到有效的混沌
        for (int i = 0; i < 500; i++) {
            x[0] = u * x[0] * (1 - x[0]);
        }
        for (int i = 0; i < size - 1; i++) {
            x[i + 1] = u * x[i] * (1 - x[i]);
        }

        //根据混沌序列生成置换表
        int[] index = sort(x);
        //位置置换
        int[] rgb_rep = new int[size];
        if (encode) {
            for (int i = 0; i < size; i++) {
                rgb_rep[i] = rgb[index[i]];
            }
        } else {
            System.arraycopy(rgb, 0, rgb_rep, 0, size);
        }

        //像素置换
        x[0] = x0_2;
        for (int i = 0; i < 500; i++) {
            x[0] = u * x[0] * (1 - x[0]);
        }
        for (int i = 0; i < size - 1; i++) {
            x[i + 1] = u * x[i] * (1 - x[i]);
        }
        for (int i = 0; i < size; i++) {
            //归一化
            //0~1的值转换0x00000000~0x00FFFFFF
            x[i] = x[i] * 0xffffff;
            //异或
            rgb_rep[i] = rgb_rep[i] ^ (int) x[i];
        }

        //解密位置置换
        if (!encode) {
            for (int i = 0; i < size; i++) {
                rgb[index[i]] = rgb_rep[i];
            }
            rgb_rep = rgb;
        }

        image.flush();
        image = new BufferedImage(width, height, image.getType());
        image.setRGB(0, 0, width, height, rgb_rep, 0, width);
        return image;
    }


    private static int[] sort(float[] x) {
        int size = x.length;
        int[] index = new int[size];//置换表
        for (int i = 0; i < size; i++) {
            index[i] = i;
        }
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (x[min] > x[j]) {
                    min = j;
                }
            }
            float temp = x[min];
            x[min] = x[i];
            x[i] = temp;

            int temp2 = index[min];
            index[min] = index[i];
            index[i] = temp2;
        }
        return index;
    }

}
