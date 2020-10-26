package com.whitemagic2014.controller.pcr;

import com.whitemagic2014.vo.ResultModel;
import com.whitemagic2014.service.PcrBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @Name: checkKnife
     * @Description: 面板查刀页面
     * @Param: gid
     * @Return: ModelAndView
     * @Author: magic chen
     * @Date: 2020/10/26 17:25
     **/
    @GetMapping("/checkKnife")
    public ModelAndView checkKnife(Long gid) {
        ModelAndView mv = new ModelAndView("pcr/knifeboard");
        ResultModel result = new ResultModel().wrapper(pbs.checkKnife(gid, true));
        mv.addAllObjects(result);
        return mv;
    }


}
