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
 * @Description: 朋友圈文案
 * @author: magic chen
 * @date: 2022/7/19 11:26
 **/
@Command
public class PYQCommand extends NoAuthCommand {

    @Autowired
    FunnyTextService funnyTextService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("pyq", "朋友圈", "发动态");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        At at = new At(sender.getId());
        return at.plus(funnyTextService.getPyq());
    }
}
