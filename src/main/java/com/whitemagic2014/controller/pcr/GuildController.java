package com.whitemagic2014.controller.pcr;

import com.whitemagic2014.vo.ResultModel;
import com.whitemagic2014.service.PcrBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: pcr guild board
 * @author: magic chen
 * @date: 2020/10/26 11:46
 **/
@Controller
@RequestMapping("/pcr/guild")
public class GuildController {

    @Autowired
    PcrBotService pbs;

    /**
     * @Name: checkKnifePage
     * @Description: 面板查刀页面
     * @Param: gid
     * @Return: ModelAndView
     * @Author: magic chen
     * @Date: 2020/10/26 17:25
     **/
    @GetMapping("/checkKnife")
    public ModelAndView checkKnifePage(@RequestParam("gid") Long gid) {
        ResultModel result = new ResultModel().wrapper(pbs.getKnifeDate(gid));
        ModelAndView mv;
        if (result.isSuccess()){
            mv = new ModelAndView("pcr/knifeboard");
            mv.addAllObjects(result);
            mv.addObject("gid", gid);
        }else {
            mv = new ModelAndView("errorPage");
            mv.addObject("status","404");
            mv.addObject("message","该公会还没有出刀记录,稍后再来查询吧");
        }
        return mv;
    }

    /**
     * @Name: checkKnife
     * @Description: 出刀数据接口
     * @Param: gid
     * @Param: dateStr
     * @Return: ModelAndView 返回 thymeleaf 模板
     * @Author: magic chen
     * @Date: 2020/10/27 14:23
     **/
    @PostMapping("/api/checkKnife")
    public ModelAndView checkKnife(@RequestParam("gid") Long gid,@RequestParam("dateStr") String dateStr) {
        ModelAndView mv = new ModelAndView("pcr/knife-list");
        mv.addAllObjects(new ResultModel().wrapper(pbs.checkKnife(gid, dateStr)));
        return mv;
    }


}
