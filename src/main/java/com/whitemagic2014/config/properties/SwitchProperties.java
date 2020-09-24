package com.whitemagic2014.config.properties;

import com.whitemagic2014.config.ReadOutDesignatedConfiguration;
import com.whitemagic2014.util.MagicSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @Description: 加载用户组件配置文件
 * @author: magic chen
 * @date: 2020/9/24 16:57
 **/
public class SwitchProperties {

    private static final Logger logger = LoggerFactory.getLogger(SwitchProperties.class);

    public static void updateSwitchByProperty() {
        logger.info("开始读取 component switch 配置");

        File in = new File(ReadOutDesignatedConfiguration.OUT_LOCATION);
        try (BufferedReader br = new BufferedReader(new FileReader(in))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("component.")) {
                    String[] temp = line.split("=");
                    MagicSwitch.init(temp[0], Boolean.valueOf(temp[1]));
                }
            }
        } catch (Exception e) {
            logger.error("读取 component switch 配置 失败", e);
        }
    }

}
