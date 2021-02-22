package com.whitemagic2014.command.impl.friend.admin;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.friend.AdminFriendCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.util.MagicMsgSender;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;

/**
 * @Description: 广播通知
 * @author: magic chen
 * @date: 2021/1/28 10:21
 **/
@Command
public class BroadcastCommand extends AdminFriendCommand {

    @Override
    protected Message executeHandle(Friend sender, ArrayList<String> args, MessageChain messageChain, Friend subject) {
        if (args.size() >= 1) {
            try {
                Message msg = new PlainText(args.stream().reduce("",String::concat));
                MagicMsgSender.sendBroadcast(msg);
                return new PlainText("发送成功");
            } catch (Exception e) {
                return new PlainText("发送失败:" + e.getMessage());
            }
        } else {
            return new PlainText("广播 [内容]");
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("广播");
    }
}
