package com.whitemagic2014.config;

import com.whitemagic2014.util.Path;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @Description: 配置读取外部配置文件的配置文件（是不是很绕口。。）
 * @author: magic chen
 * @date: 2020/8/22 14:46
 **/
public class ReadOutDesignatedConfiguration implements EnvironmentPostProcessor {

    // 注意:只能读 .propertie .yml可能会有问题
    private  String OUT_LOCATION = Path.getPath()+"WMagicBotR.properties";


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        File file = new File(OUT_LOCATION);
        if (file.exists()) {
            MutablePropertySources propertySources = environment.getPropertySources();
            Properties properties = loadProperties(file);
            // 将读取的外部配置文件优先性加至 队首 （即读外部的）
            propertySources.addFirst(new PropertiesPropertySource("Config", properties));
            // 将读取的外部配置文件优先性加至 末尾 （即读内部的）
            // propertySources.addLast(new PropertiesPropertySource("Config", properties));
            System.out.println("读取外部配置");
        } else {
            System.out.println("外部文件不存在 读取内部配置");
        }
    }

    private Properties loadProperties(File f) {
        FileSystemResource resource = new FileSystemResource(f);
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load local settings from " + f.getAbsolutePath(), ex);
        }
    }
}
