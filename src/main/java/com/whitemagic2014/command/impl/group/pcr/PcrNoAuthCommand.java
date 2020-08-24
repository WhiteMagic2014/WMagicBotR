package com.whitemagic2014.command.impl.group.pcr;

import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;

/**
 * @Description: pcr 工会战 无权限指令
 * @author: magic chen
 * @date: 2020/8/23 13:16
 **/
public abstract class PcrNoAuthCommand extends PcrBaseCommand {

    @Override
    protected PrivateModel<String> checkRole(Member sender, Group subject) {
        //无需权限
        return new PrivateModel<>(ReturnCode.SUCCESS, "sucess");
    }
}
