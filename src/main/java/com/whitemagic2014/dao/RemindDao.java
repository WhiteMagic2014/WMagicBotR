package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.Remind;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 备忘dao
 * @author: magic chen
 * @date: 2023/4/13 16:50
 **/
@Repository
public interface RemindDao {


    /**
     * 读取  时间小于<nowL  未被取消的备忘
     *
     * @param nowL
     * @return
     */
    List<Remind> loadReminds(@Param("nowL") Long nowL);


    /**
     * 取消备忘
     *
     * @param taskKey
     * @return
     */
    int cancelRemind(@Param("taskKey") String taskKey);

    /**
     * 新增备忘
     *
     * @param remind
     * @return
     */
    int addRemind(Remind remind);

}
