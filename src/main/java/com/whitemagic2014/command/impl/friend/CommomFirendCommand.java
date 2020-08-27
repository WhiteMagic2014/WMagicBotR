package com.whitemagic2014.command.impl.friend;

import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Friend;

/**
 * @Description: 普通私聊指令
 * @author: magic chen
 * @date: 2020/8/27 17:15
 **/
public abstract class CommomFirendCommand extends BaseFriendCommand {

    @Override
    protected PrivateModel<String> checkRole(Friend sender, Friend subject) {
        return new PrivateModel<>(ReturnCode.SUCCESS, "sucess");
    }
}
