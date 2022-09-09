package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.util.time.MagicTaskObserver;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;

/**
 * @Description: 取消备忘
 * @author: magic chen
 * @date: 2022/9/8 17:30
 **/
@Command
public class RemindCancelCommand extends NoAuthCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("取消备忘");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        String taskKey = args.get(0);
        MagicTaskObserver.cancelTask(taskKey);
        return new PlainText("已取消备忘提醒");
    }
}
