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
 * @Description: 查询sl
 * @author: magic chen
 * @date: 2020/8/23 23:13
 **/
@Component
public class SLcheck extends PcrNoAuthCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("sl？", "sl?");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<String> result = pcrBotService.sl(subject.getId(), sender.getId(), true);
        return simpleMsg(sender, result);
    }


}
