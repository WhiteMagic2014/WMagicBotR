package com.whitemagic2014.service;

import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcrjjc.Answer;

import java.util.List;

/**
 * @Description: pcr jjc search
 * @author: magic chen
 * @date: 2020/8/25 17:42
 **/
public interface Pcrjjc {


    /**
     * @Name: initNameFile
     * @Description: 初始化pcr name文件
     * @Param:
     * @Author: magic chen
     * @Date: 2020/8/26 16:31
     **/
    void initNameFile();

    /**
     * @Name: refreshNameFile
     * @Description: 刷新pcr name文件 当更新的时候可以手动触发
     * @Param: url
     * @Return: String
     * @Author: magic chen
     * @Date: 2020/8/26 14:55
     **/
    PrivateModel<String> refreshNameFile(String url);


    /**
     * @Name: checkjjc
     * @Description: jjc查询
     * @Param: names
     * @Param: region
     * @Return:
     * @Author: magic chen
     * @Date: 2020/8/26 17:17
     **/
    PrivateModel<List<Answer>> checkjjc(List<String> names, Integer region);


    /**
     * @Name: id2Name
     * @Description: id转name 如果有多个名字 随机返回一个中文名
     * @Param: pcr role id
     * @Return: pcr role name
     * @Author: magic chen
     * @Date: 2020/8/26 18:22
     **/
    String id2Name(Integer id);

}
