package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.Chp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 彩虹屁数据存储
 * @author: magic chen
 * @date: 2022/7/18 11:16
 **/
@Repository
public interface ChpDao {

    //加载全部
    List<Chp> loadAll();

    int insert(Chp data);

    Chp getByHash(@Param("hash") String hash);

}
