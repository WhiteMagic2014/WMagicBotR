package com.whitemagic2014.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 测试集成web
 * @author: magic chen
 * @date: 2020/8/24 15:22
 **/
@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping()
    @ResponseBody
    public String hello(){
        return "hello";
    }

}
