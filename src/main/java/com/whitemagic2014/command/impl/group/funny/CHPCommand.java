package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.FunnyTextService;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @Description: 夸夸机器人/ 在思考要不要喷喷机器人。。。
 * @author: magic chen
 * @date: 2022/7/18 10:51
 **/
@Command
public class CHPCommand extends NoAuthCommand {

    @Autowired
    FunnyTextService funnyTextService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("chp", "夸", "继续夸", "夸我", "夸夸我", "说句好听的");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        At at = new At(sender.getId());
        return at.plus(funnyTextService.getChp());
    }
}
