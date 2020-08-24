package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 查树
 * @author: magic chen
 * @date: 2020/8/24 20:41
 **/
@Component
public class CheckTree extends PcrNoAuthCommand {
    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<List<Long>> result = pcrBotService.checkTree(subject.getId());
        if (!result.isSuccess()) {
            return simpleErrMsg(sender,result);
        }
        List<Long> uids = result.getReturnObject();
        MessageChain ats = makeAts(uids,subject);
        return new PlainText("现在树上的朋友有:\n").plus(ats);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("查树");
    }
}
