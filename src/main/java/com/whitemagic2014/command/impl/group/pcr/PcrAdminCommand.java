package com.whitemagic2014.command.impl.group.pcr;

import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;

/**
 * @Description: pcr 工会战 管理员及以上指令
 * @author: magic chen
 * @date: 2020/8/23 12:54
 **/
public abstract class PcrAdminCommand extends PcrBaseCommand {

    @Override
    protected PrivateModel<String> checkRole(Member sender, Group subject) {
        if (sender.getId() == adminUid  //超管
                || isGroupAdmin(sender) //群管理
                || isGroupOwner(sender) //群主
                || isAdmin(subject.getId(), sender.getId()) //bot管理
        ) {
            return new PrivateModel<>(ReturnCode.SUCCESS, "sucess");
        } else {
            return new PrivateModel<>(ReturnCode.FAIL, "无权操作,该指令需要[群主][群管理员][超管][bot管理员]权限");
        }
    }
}
