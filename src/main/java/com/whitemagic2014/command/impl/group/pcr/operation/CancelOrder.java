package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.vo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 取消预约boss
 * @author: magic chen
 * @date: 2020/8/24 22:29
 **/
@Component
public class CancelOrder extends PcrNoAuthCommand {

    String txt = "取消预约/取消预定/取消 [1-5]";

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {

        try {
            int num = Integer.parseInt(args.get(0));
            if (num < 1 || num > 5) {
                return new At(sender).plus("指令错误: " + txt);
            }
            PrivateModel<String> result = pcrBotService.cancelOrder(subject.getId(), sender.getId(), num);
            return simpleMsg(sender, result);
        } catch (Exception e) {
            return new At(sender).plus("指令错误:" + txt);
        }

    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("取消预约", "取消", "取消预定");
    }
}
