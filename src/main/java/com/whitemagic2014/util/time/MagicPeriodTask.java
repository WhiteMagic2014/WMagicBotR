package com.whitemagic2014.util.time;

import java.util.Date;

/**
 * @Description: 扩展的 TimerTask 执行循环任务
 * @author: magic chen
 * @date: 2020/9/30 16:40
 **/
public abstract class MagicPeriodTask extends MagicTask {

    public MagicPeriodTask(String key) {
        super(key);
    }

    // 循环执行 在执行后不做特殊处理
    @Override
    public void run() {
        logger.info("PeriodTask [" + taskKey + "] Fired");
        handle();
    }

    public void schedule(long delay, long period) {
        magicTimer.schedule(this, delay, period);
    }

    public void schedule(Date firstTime, long period) {
        magicTimer.schedule(this, firstTime, period);
    }

    public void scheduleAtFixedRate(long delay, long period) {
        magicTimer.scheduleAtFixedRate(this, delay, period);
    }

    public void scheduleAtFixedRate(Date firstTime, long period) {
        magicTimer.scheduleAtFixedRate(this, firstTime, period);
    }

}
