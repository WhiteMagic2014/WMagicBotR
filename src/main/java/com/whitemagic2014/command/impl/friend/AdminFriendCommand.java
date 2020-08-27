package com.whitemagic2014.command.impl.friend;

import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Friend;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description: 超管权限 私聊指令
 * @author: magic chen
 * @date: 2020/8/27 17:17
 **/
public abstract class AdminFriendCommand extends BaseFriendCommand {

    @Value("${bot.admin}")
    protected Long adminUid;

    @Override
    protected PrivateModel<String> checkRole(Friend sender, Friend subject) {
        if (sender.getId() == adminUid) {
            return new PrivateModel<>(ReturnCode.SUCCESS, "sucess");
        } else {
            return new PrivateModel<>(ReturnCode.FAIL, "无权操作,该指令需要[超管]权限");
        }
    }

}
