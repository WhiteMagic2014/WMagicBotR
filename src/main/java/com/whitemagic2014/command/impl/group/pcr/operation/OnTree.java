package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 挂树
 * @author: magic chen
 * @date: 2020/8/24 20:32
 **/
@Component
public class OnTree extends PcrNoAuthCommand {


    @Override
    public CommandProperties properties() {
        return new CommandProperties("挂树");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<String> result = pcrBotService.hangOnTree(subject.getId(),sender.getId());
        return simpleMsg(sender,result);
    }


}
