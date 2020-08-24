package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrAdminCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 加入全部成员
 * @author: magic chen
 * @date: 2020/8/23 17:42
 **/
@Component
public class AddMemberAll extends PcrAdminCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("加入全部成员");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<String> result = pcrBotService.addAllMemer(subject);
        return simpleMsg(sender, result);
    }


}
