package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Description: roll 点
 * @author: magic chen
 * @date: 2020/8/21 16:33
 **/
@Component
public class RollCommand extends NoAuthCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("roll", "ROLL", "Roll");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        Random random = new Random();
        if (!args.isEmpty() && args.size() != 1) {
            return new PlainText("指令错误: [指令前缀][roll] [数字(可省略)]");
        } else {
            At at = new At(sender);
            PlainText plainText = null;
            if (args.isEmpty()) {
                plainText = new PlainText(" roll出 " + (random.nextInt(100) + 1));
            } else {
                int range = 0;
                try {
                    range = Integer.valueOf(args.get(0));
                } catch (NumberFormatException e) {
                    return new PlainText("指令错误: [指令前缀][roll] [数字(可省略)]");
                }
                if (range > Integer.MAX_VALUE) {
                    return new PlainText("指令错误: 数字范围 0 ~ " + Integer.MAX_VALUE);
                }
                plainText = new PlainText(" roll出 " + (random.nextInt(range) + 1));
            }
            return at.plus(plainText);
        }
    }


}
