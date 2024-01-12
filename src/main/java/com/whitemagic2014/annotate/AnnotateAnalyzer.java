package com.whitemagic2014.annotate;

import com.github.WhiteMagic2014.function.GmpFunction;
import com.whitemagic2014.command.Command;
import com.whitemagic2014.gmpfunction.FunctionPool;
import com.whitemagic2014.util.MagicSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 自定义注解处理
 * @author: magic chen
 * @date: 2020/9/24 10:32
 **/
@Component
public class AnnotateAnalyzer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AnnotateAnalyzer.class);

    // 指令事件 需要注册的 指令
    List<Command> commands = new ArrayList<>();

    @Autowired
    FunctionPool functionPool;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            // @Command 注解 自动注册指令
            logger.info("开始扫描注册 command");
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(com.whitemagic2014.annotate.Command.class);
            for (Object bean : beans.values()) {
                if (bean instanceof com.whitemagic2014.command.Command) {
                    com.whitemagic2014.annotate.Command com = bean.getClass().getAnnotation(com.whitemagic2014.annotate.Command.class);
                    if (com.autoLoad()) {
                        commands.add((com.whitemagic2014.command.Command) bean);
                    }
                }
            }

            // @Switch 注解自动 注册入MagicSwitch
            logger.info("开始注册 component switch");
            Map<String, Object> switchBeans = event.getApplicationContext().getBeansWithAnnotation(Switch.class);
            for (Object bean : switchBeans.values()) {
                // 只有指令才去登记 非指令不登记
                if (bean instanceof Command) {
                    Switch sw = bean.getClass().getAnnotation(Switch.class);
                    MagicSwitch.init(sw.name(), sw.defaultOn());
                }
            }

            // @Function 注解扫描注册 Function 进入 ChatGptCommand
            logger.info("开始注册 Function");
            Map<String, Object> functionBeans = event.getApplicationContext().getBeansWithAnnotation(Function.class);
            for (Object bean : functionBeans.values()) {
                // 只判定 GmpFunction
                if (bean instanceof com.github.WhiteMagic2014.function.GmpFunction) {
                    Function fun = bean.getClass().getAnnotation(Function.class);
                    if (fun.autoLoad()) {
                        functionPool.addFunction((GmpFunction) bean);
                    }
                }
            }

        }
    }

    public List<Command> getCommands() {
        return commands;
    }
}
