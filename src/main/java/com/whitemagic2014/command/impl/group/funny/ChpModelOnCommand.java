package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.ChpService;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @Description: 开启夸夸模式
 * @author: magic chen
 * @date: 2022/7/18 12:05
 **/
@Command
public class ChpModelOnCommand extends NoAuthCommand {


    @Autowired
    ChpService chpService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("开启夸夸模式", "开启舔狗模式");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        chpService.registChp(subject.getId(), sender.getId());


        return new PlainText("注册");
    }
}
