package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.UserPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 用户计划表 dao
 * @author: magic chen
 * @date: 2020/8/21 17:19
 **/
@Repository
public interface UserPlanDao {

    List<UserPlan> findPlans(@Param("uid") String uid, @Param("itemName") String itemName);

    int instertPlan(UserPlan plan);

    int updatePlan(UserPlan plan);

    int deletePlan(@Param("id") Integer id);

}
