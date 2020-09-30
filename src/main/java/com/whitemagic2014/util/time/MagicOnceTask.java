package com.whitemagic2014.util.time;

import java.util.Date;

/**
 * @Description: 扩展的 TimerTask 执行单次任务 执行后从注册中心将自己移除
 * @author: magic chen
 * @date: 2020/9/30 16:43
 **/
public abstract class MagicOnceTask extends MagicTask {

    public MagicOnceTask(String key) {
        super(key);
    }


    @Override
    public void run() {
        logger.info("OnceTask [" + taskKey + "] Fired");
        handle();
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
