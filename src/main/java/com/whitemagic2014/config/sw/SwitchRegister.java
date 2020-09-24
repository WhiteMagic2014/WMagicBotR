package com.whitemagic2014.config.sw;

import com.whitemagic2014.command.Command;
import com.whitemagic2014.util.MagicSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: @Switch 注解自动 注册入MagicSwitch
 * @author: magic chen
 * @date: 2020/9/24 10:32
 **/
@Component
public class SwitchRegister implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SwitchRegister.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("开始注册 component switch");
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(Switch.class);
            for (Object bean : beans.values()) {
                // 只有指令才去登记 非指令不登记
                if (bean instanceof Command) {
                    Switch sw = bean.getClass().getAnnotation(Switch.class);
                    MagicSwitch.init(sw.name(), sw.defaultOn());
                }
            }
        }
    }


}
