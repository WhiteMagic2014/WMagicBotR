package com.whitemagic2014.command.impl.friend.admin;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.friend.AdminFriendCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.ChatPGTService;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;

/**
 * 重新加载问答预训练数据向量集合
 */
@Command
public class ReloadVector extends AdminFriendCommand {

    @Autowired
    @Qualifier("ChatPGTServiceImpl")
    ChatPGTService service;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("reloadvector");
    }

    @Override
    protected Message executeHandle(Friend sender, ArrayList<String> args, MessageChain messageChain, Friend subject) throws Exception {
        return new PlainText(service.reloadVector());
    }
}
