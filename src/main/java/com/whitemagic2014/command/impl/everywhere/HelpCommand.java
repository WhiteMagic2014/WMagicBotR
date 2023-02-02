package com.whitemagic2014.command.impl.everywhere;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;

/**
 * @Description: help
 * @author: magic chen
 * @date: 2020/9/4 17:06
 **/
@Command
public class HelpCommand extends BaseEveryWhereCommand {

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        return new PlainText("https://github.com/WhiteMagic2014/WMagicBotR/blob/master/CommandBook.md\n"
                +"github打不开试试这个:\n" +
                "https://gitee.com/WhiteMagic2014/WMagicBotR/blob/master/CommandBook.md");
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("help", "帮助");
    }
}
