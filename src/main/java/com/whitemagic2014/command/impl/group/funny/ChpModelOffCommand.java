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
 * @Description: 关闭夸夸模式
 * @author: magic chen
 * @date: 2022/7/18 12:08
 **/
@Command
public class ChpModelOffCommand extends NoAuthCommand {

    @Autowired
    ChpService chpService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("关闭夸夸模式", "关闭舔狗模式");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        chpService.unRegistChp(subject.getId(), sender.getId());
        return new PlainText("已为您关闭夸夸模式");
    }
}
