package com.whitemagic2014.config;

import com.github.WhiteMagic2014.Gmp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 无法直接使用 Service Controller Component ，但有需要被spring管理的 实例
 * @author: magic chen
 * @date: 2020/9/1 11:48
 **/
@Configuration
public class Beans {


    @Value("${ChatGPT.proxyServer}")
    private String proxyServer;

    @Value("${ChatGPT.key}")
    private String key;

    @Value("${ChatGPT.org}")
    private String org;

    @Bean
    public Gmp initGmp() {
        Gmp gmp = new Gmp(proxyServer, key);
        gmp.setOrg(org);
        return gmp;
    }


}
