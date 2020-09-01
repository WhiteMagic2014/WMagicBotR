package com.whitemagic2014.command.impl.everywhere;

import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.MiraiLogger;
import net.mamoe.mirai.utils.Utils;
import org.springframework.stereotype.Component;


import java.util.ArrayList;


/**
 * @Description: 测试command
 * @author: magic chen
 * @date: 2020/8/20 23:12
 **/
@Component
public class HelloCommand extends BaseEveryWhereCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("hello");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        return new PlainText("hello");
    }
}
