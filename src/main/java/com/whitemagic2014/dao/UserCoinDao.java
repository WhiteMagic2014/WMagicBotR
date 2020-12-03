package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.currency.UserCoin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: 货币dao
 * @author: magic chen
 * @date: 2020/12/2 17:12
 **/
@Repository
public interface UserCoinDao {


    /**
     * @Name: findByUid
     * @Description: 根据uid 查询账户
     * @Param: uid
     * @Return: UserCoin
     * @Author: magic chen
     * @Date: 2020/12/2 17:24
     **/
    UserCoin findByUid(@Param("uid") Long uid);

    /**
     * @Name: insert
     * @Description: 新增账户
     * @Param: record
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/12/2 17:28
     **/
    int insert(UserCoin record);

    /**
     * @Name: updateByUid
     * @Description: 变更账户金额
     * @Param: update
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/12/2 17:29
     **/
    int updateByUid(UserCoin update);

    /**
     * @Name: delete
     * @Description: 注销账户
     * @Param: uid
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/12/2 17:29
     **/
    int delete(@Param("uid") Long uid);


}
