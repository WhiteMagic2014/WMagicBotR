package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.FunnyText;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 各种文本数据存储
 * @author: magic chen
 * @date: 2022/7/18 11:16
 **/
@Repository
public interface FunnyTextDao {
    // 彩虹屁
    List<FunnyText> loadAllChp();

    int insertChp(FunnyText data);

    FunnyText getChpByHash(@Param("hash") String hash);


    //朋友圈文案
    List<FunnyText> loadAllPyq();

    int insertPyq(FunnyText data);

    FunnyText getPyqByHash(@Param("hash") String hash);

    // 毒鸡汤
    List<FunnyText> loadAllDjt();

    int insertDjt(FunnyText data);

    FunnyText getDjtByHash(@Param("hash") String hash);


}
