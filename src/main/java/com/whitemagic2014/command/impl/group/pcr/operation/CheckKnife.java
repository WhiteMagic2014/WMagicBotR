package com.whitemagic2014.command.impl.group.pcr.operation;

import com.alibaba.fastjson.JSON;
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
 * @Description: 查刀 格式化未完成,现在只返回json,后期有空优化
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

        return new PlainText(JSON.toJSONString(data));
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("查刀");
    }
}
