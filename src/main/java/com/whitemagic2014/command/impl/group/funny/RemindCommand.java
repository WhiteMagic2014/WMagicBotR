package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.util.DateFormatUtil;
import com.whitemagic2014.util.MagicMsgSender;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;

/**
 * @Description: 描述这个类的作用
 * @author: magic chen
 * @date: 2021/1/7 17:15
 **/
@Command
public class RemindCommand extends NoAuthCommand {

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        if (args.size() != 2){
            return new PlainText("备忘 yyyy-MM-dd/HH:mm:ss [备忘内容]");
        }
        String dateStr = args.get(0).replace("/"," ");
        MagicMsgSender.sendGroupMsgTiming(subject.getId(),new At(sender).plus(args.get(1)), DateFormatUtil.sdf.parse(dateStr));
        return new PlainText("好的");
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("备忘");
    }
}
