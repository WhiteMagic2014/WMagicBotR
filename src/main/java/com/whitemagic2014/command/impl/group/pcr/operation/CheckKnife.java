package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcr.Guild;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 查刀 格式化未完成,现在只返回json,后期有空优化
 * @author: magic chen
 * @date: 2020/8/24 23:30
 **/
@Component
public class CheckKnife extends PcrNoAuthCommand {

    @Value("${site.url}")
    String siteUrl;

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<Guild> gexist = pcrBotService.checkGuildExist(subject.getId());
        if (!gexist.isSuccess()) {
            return simpleErrMsg(sender, gexist);
        }
        if (!siteUrl.endsWith("/")){
            siteUrl = siteUrl.concat("/");
        }
        String url = siteUrl + "botr/pcr/guild/checkKnife?gid="+subject.getId();
        return new PlainText("查刀移步:\n" + url);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("查刀");
    }
}
