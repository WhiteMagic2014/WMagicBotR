package com.whitemagic2014.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @Description: 全局异常处理
 * @author: magic chen
 * @date: 2020/8/31 14:52
 **/
@ControllerAdvice
public class GlobalException {

    private final static Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    public void defaultErrorHandler(Exception e) {
        try {
            logger.error("全局异常处理：", e);
        } catch (Exception ex) {
            logger.error("全局异常处理异常：", ex);
        }
    }

}
