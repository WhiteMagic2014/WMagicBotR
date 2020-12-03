package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.currency.CoinLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 货币变更记录 dao
 * @author: magic chen
 * @date: 2020/12/2 17:30
 **/
@Repository
public interface CoinLogDao {

    int insert(CoinLog log);

    List<CoinLog> selectByCondition(@Param("uid") Long uid, @Param("gid") Long gid, @Param("type") String type);

}
