package com.whitemagic2014.command.impl.group.chatPgt;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.annotate.Switch;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.dic.Dic;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.ChatPGTService;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Command
@Switch(name = Dic.Component_ChatGPT)
public class ChatGptCommand extends NoAuthCommand {

    @Autowired
    ChatPGTService service;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("gpt");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        if (subject.getId() != 362656777L) {
            return null;
        }
        String prompt = args.stream().map(s -> {
            return s.concat(" ");
        }).reduce("", String::concat);

        return new At(sender.getId()).plus(service.chat(prompt));
    }
}
