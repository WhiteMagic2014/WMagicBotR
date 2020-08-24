package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.CanEat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 私人特供方法
 * @author: magic chen
 * @date: 2020/8/21 18:26
 **/
@Repository
public interface CanEatDao {

    int insert(CanEat ce);

    List<CanEat> findByName(@Param("itemName") String itemName);

}
