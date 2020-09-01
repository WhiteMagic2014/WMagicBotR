package com.whitemagic2014.util;

import net.mamoe.mirai.utils.MiraiLoggerPlatformBase;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 用spring 的slf4j 实现  mirai 的log接口,让bot 层的log重定向到 spring log下
 * @author: magic chen
 * @date: 2020/9/1 11:18
 **/
public class MagicLogger extends MiraiLoggerPlatformBase {

    private static final Logger logger = LoggerFactory.getLogger("WMagicBotR");

    @Override
    protected void debug0(@Nullable String s, @Nullable Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    protected void error0(@Nullable String s, @Nullable Throwable throwable) {
        logger.error(s, throwable);
    }

    @Override
    protected void info0(@Nullable String s, @Nullable Throwable throwable) {
        logger.info(s, throwable);
    }

    @Override
    protected void verbose0(@Nullable String s, @Nullable Throwable throwable) {
        logger.info(s, throwable);
    }

    @Override
    protected void warning0(@Nullable String s, @Nullable Throwable throwable) {
        logger.warn(s, throwable);
    }

    @Nullable
    @Override
    public String getIdentity() {
        return "WMagicBotR";
    }


}
