package com.whitemagic2014.command.impl.group;

import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;

/**
 * @Description: 群主超管指令
 * @author: magic chen
 * @date: 2020/8/23 13:35
 **/
public abstract class OwnerCommand extends BaseGroupCommand {

    @Override
    protected PrivateModel<String> checkRole(Member sender, Group subject) {
        if (sender.getId() == adminUid  //超管
                || isGroupOwner(sender) //群主
        ) {
            return new PrivateModel<>(ReturnCode.SUCCESS, "sucess");
        } else {
            return new PrivateModel<>(ReturnCode.FAIL, "无权操作,该指令需要[群主][超管]权限");
        }
    }
}
