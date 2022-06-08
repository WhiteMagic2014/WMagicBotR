package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.util.MagicMsgSender;
import com.whitemagic2014.vo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.ArrayList;

/**
 * @Description: 挂树
 * @author: magic chen
 * @date: 2020/8/24 20:32
 **/
@Command
public class OnTree extends PcrNoAuthCommand {


    @Override
    public CommandProperties properties() {
        return new CommandProperties("挂树");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<String> result = pcrBotService.hangOnTree(subject.getId(),sender.getId());
        if (result.isSuccess()){
            MagicMsgSender.sendGroupMsgDelay(subject.getId(), new At(sender.getId()).plus("挂树30分钟提醒,请注意时间,避免掉刀(如已下树请忽略)"),1800L);
        }
        return simpleMsg(sender,result);
    }


}
