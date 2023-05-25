package com.whitemagic2014.command.impl.everywhere.chatGpt;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.annotate.Switch;
import com.whitemagic2014.command.impl.everywhere.BaseEveryWhereCommand;
import com.whitemagic2014.dic.Dic;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.ChatPGTService;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Command
@Switch(name = Dic.Component_ChatGPT)
public class ChatGptCommand extends BaseEveryWhereCommand {

    @Autowired
    ChatPGTService service;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("gpt", "xml");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        String session = subject.getId() + "-" + sender.getId();
        String prompt = args.stream().map(s -> {
            return s.concat(" ");
        }).reduce("", String::concat);
        return simpleMsg(sender, new PlainText(service.chat(session, prompt)));
    }
}
