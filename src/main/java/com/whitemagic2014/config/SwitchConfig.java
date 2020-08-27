package com.whitemagic2014.config;

import com.whitemagic2014.dic.Dic;
import com.whitemagic2014.util.MagicSwitch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 全局动态开关配置
 * @author: magic chen
 * @date: 2020/8/27 17:53
 **/
@Configuration
public class SwitchConfig {

    @Value("${pcr.guild}")
    Boolean pcrGuildSW;

    @Bean
    public MagicSwitch createSwitch() {
        MagicSwitch ms = new MagicSwitch();
        ms.init(Dic.Pcr_Guild_Component, pcrGuildSW);
        return ms;
    }

}
