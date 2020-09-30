package com.whitemagic2014.util.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: 扩展的 TimerTask
 * @author: magic chen
 * @date: 2020/9/30 15:08
 **/
public abstract class MagicTask extends TimerTask {

    protected static final Logger logger = LoggerFactory.getLogger(MagicTask.class);

    protected static Timer magicTimer = new Timer("magicTimer");

    protected String taskKey;

    /**
     * @Name:
     * @Description: 在创建时候 在观察中心注册
     * @Param: null
     * @Return:
     * @Author: magic chen
     * @Date: 2020/9/30 15:29
     **/
    public MagicTask(String key) {
        taskKey = key;
        logger.info("Task ["+key+"] Regist");
        MagicTaskObserver.addTask(key, this);
    }


    @Override
    public boolean cancel() {
        // 在取消任务的时候 从 注册中心 移除 task
        MagicTaskObserver.removeTask(taskKey);
        return super.cancel();
    }

    public abstract void handle();

}
