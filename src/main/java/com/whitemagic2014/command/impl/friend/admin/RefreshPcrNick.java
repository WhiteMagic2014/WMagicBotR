package com.whitemagic2014.command.impl.friend.admin;

import com.whitemagic2014.command.impl.friend.AdminFriendCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.service.Pcrjjc;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 刷新nick name
 * @author: magic chen
 * @date: 2020/8/27 17:10
 **/
@Component
public class RefreshPcrNick extends AdminFriendCommand {

    @Autowired
    Pcrjjc pcrjjc;

    @Override
    protected Message executeHandle(Friend sender, ArrayList<String> args, MessageChain messageChain, Friend subject) {
        PrivateModel<String> result = pcrjjc.refreshNameFile(null);
        if (!result.isSuccess()) {
            return new PlainText(result.getReturnMessage());
        } else {
            return new PlainText(result.getReturnObject());
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("刷新nickname");
    }
}
