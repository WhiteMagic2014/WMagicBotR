package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 出刀
 * @author: magic chen
 * @date: 2020/8/23 19:17
 **/
@Component
public class AttackKnife extends PcrNoAuthCommand {

    String txt = "报刀 伤害 [@某人 可选] [昨日 可选]";

    @Override
    public CommandProperties properties() {
        return new CommandProperties("报刀");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {

        int size = args.size();
        try {
            PrivateModel<String> result;
            Long damage = Long.parseLong(args.get(0));
            if (size == 1) {
                // 报自己
                System.out.println("报刀 自己");
                result = pcrBotService.attackKnife(subject.getId(), sender.getId(), damage, false);
            } else if (size == 2) {
                // 需要额外判断 是 昨日报刀 还是 代报
                if (args.get(1).contains("@")) {
                    //代报
                    System.out.println("报刀 代报");
                    At at = messageChain.first(At.Key);
                    result = pcrBotService.attackKnife(subject.getId(), at.getTarget(), damage, false);
                } else {
                    //报自己昨日刀
                    System.out.println("报刀 自己 昨日");
                    result = pcrBotService.attackKnife(subject.getId(), sender.getId(), damage, true);
                }
            } else if (size == 3) {
                // 代报昨日
                System.out.println("报刀 代报 昨日");
                At at = messageChain.first(At.Key);
                result = pcrBotService.attackKnife(subject.getId(), at.getTarget(), damage, true);
            } else {
                return new At(sender).plus("指令错误," + txt);
            }
            return simpleMsg(sender, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new At(sender).plus("指令错误." + txt);
        }

    }


}
