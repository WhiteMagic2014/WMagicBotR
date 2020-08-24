package com.whitemagic2014.command.impl.group.pcr;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.dao.PcrDao;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;



/**
 * @Description: 测试command
 * @author: magic chen
 * @date: 2020/8/20 23:12
 **/
@Component
public class HelloCommand extends PcrNoAuthCommand {

    @Autowired
    PcrDao pcrDao;


    @Override
    public CommandProperties properties() {
        return new CommandProperties("test");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        System.out.println(sender.getNameCard());
        System.out.println(sender.getNick());

        At at = messageChain.first(At.Key);
        Member member = subject.get(at.getTarget());

        System.out.println(member.getNick());
        System.out.println(member.getNameCard());

        return null;

    }


}
