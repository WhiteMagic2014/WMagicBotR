package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 添加成员
 * @author: magic chen
 * @date: 2020/8/23 18:36
 **/
@Component
public class AddMember extends PcrNoAuthCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("加入公会", "加入工会");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<String> result;
        if (args.isEmpty()) {
            // 加入自己
            result = pcrBotService.addMemer(subject.getId(), sender.getId(), sender.getNameCard(), sender.getPermission());
        } else {
            // 加入别人 只有管理员可以添加其他群员
            if (sender.getId() == adminUid || isGroupAdmin(sender) || isGroupOwner(sender) || isAdmin(subject.getId(), sender.getId())) {
                try {
                    At at = messageChain.first(At.Key);
                    Member ated = subject.get(at.getTarget());
                    result = pcrBotService.addMemer(subject.getId(), ated.getId(), ated.getNick(), ated.getPermission());
                } catch (Exception e) {
                    return new At(sender).plus("指令错误,请@出需要添加的成员");
                }
            } else {
                return new At(sender).plus(" 无权操作,添加其他成员需要[群主][群管理员][超管]权限");
            }
        }
        return simpleMsg(sender, result);
    }


}
