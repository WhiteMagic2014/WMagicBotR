package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.EngageBattle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: EngageBattleDao
 * @author: magic chen
 * @date: 2023/1/29 12:02
 **/
@Repository
public interface EngageBattleDao {

    int insert(EngageBattle record);

    int updateByKey(EngageBattle record);

    EngageBattle selectByKey(@Param("key") String key);

    List<EngageBattle> listAllAbleBattle();

}
