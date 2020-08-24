package com.whitemagic2014.command.impl.group;

import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;

/**
 * @Description: 无需权限的指令
 * @author: magic chen
 * @date: 2020/8/23 13:39
 **/
public abstract class NoAuthCommand extends BaseGroupCommand {

    @Override
    protected PrivateModel<String> checkRole(Member sender, Group subject) {
        return new PrivateModel<>(ReturnCode.SUCCESS, "sucess");
    }

}
