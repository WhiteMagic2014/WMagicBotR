package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcr.Battle;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 查刀
 * @author: magic chen
 * @date: 2020/8/24 23:30
 **/
@Component
public class CheckKnife extends PcrNoAuthCommand {
    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {

        PrivateModel<List<Battle>> result = pcrBotService.checkKnife(subject.getId(), false);
        if (!result.isSuccess()) {
            return simpleErrMsg(sender, result);
        }

        List<Battle> data = result.getReturnObject();

        String str = data.stream().map(Battle::toString).map(s -> s.concat("\n")).reduce("", String::concat);

        return new PlainText(str);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("查刀");
    }
}
