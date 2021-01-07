package com.whitemagic2014.util.time;

import java.util.Date;

/**
 * @Description: 扩展的 TimerTask 执行单次任务 执行后从注册中心将自己移除
 * @author: magic chen
 * @date: 2020/9/30 16:43
 **/
public class MagicOnceTask extends MagicTask {


    public static MagicOnceTask build(String key, Task taskTk) {
        return new MagicOnceTask(key, taskTk);
    }

    /**
     * 请使用 build 方法获取实例
     *
     * @param key
     * @param taskTk
     */
    private MagicOnceTask(String key, Task taskTk) {
        super(key, taskTk);
    }

    @Override
    public void run() {
        logger.info("OnceTask [" + taskKey + "] Fired");
        taskTk.handle();
        // 单次任务 执行后从注册中心将自己移除
        cancel();
    }

    public void schedule(long delay) {
        magicTimer.schedule(this, delay);
    }

    public void schedule(Date time) {
        magicTimer.schedule(this, time);
    }

}
