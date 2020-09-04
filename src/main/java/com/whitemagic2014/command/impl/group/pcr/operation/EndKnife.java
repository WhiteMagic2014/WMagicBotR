package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.dic.PcrNoticeType;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 尾刀
 * @author: magic chen
 * @date: 2020/8/23 21:45
 **/
@Component
public class EndKnife extends PcrNoAuthCommand {

    String txt = "尾刀 [@某人 可选] [昨日 可选]";

    @Override
    public CommandProperties properties() {
        return new CommandProperties("尾刀");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {

        int size = args.size();
        try {
            PrivateModel<Map<String, String>> result;
            if (size == 0) {
                // 报自己
                System.out.println("尾刀 自己");
                result = pcrBotService.endKnife(subject.getId(), sender.getId(), false);
            } else if (size == 1) {
                // 需要额外判断 是 昨日报刀 还是 代报
                if (args.get(0).contains("@")) {
                    //代报
                    System.out.println("尾刀 代报");
                    At at = messageChain.first(At.Key);
                    result = pcrBotService.endKnife(subject.getId(), at.getTarget(), false);
                } else {
                    //报自己昨日刀
                    System.out.println("尾刀 自己 昨日");
                    result = pcrBotService.endKnife(subject.getId(), sender.getId(), true);
                }
            } else if (size == 2) {
                // 代报昨日
                System.out.println("尾刀 代报 昨日");
                At at = messageChain.first(At.Key);
                result = pcrBotService.endKnife(subject.getId(), at.getTarget(), true);
            } else {
                return new At(sender).plus("指令错误," + txt);
            }

            //需要自定义回复
            if (!result.isSuccess()) {
                return simpleErrMsg(sender, result);
            }

            Message nomal = new At(sender).plus(result.getReturnObject().get("nomal"));
            subject.sendMessage(nomal);

            Map<String, List<Long>> ats = result.getAts();
            if (ats.containsKey(PcrNoticeType.tree.name())) {
                Message tree = makeAts(ats.get(PcrNoticeType.tree.name()), subject)
                        .plus("\n" + result.getReturnObject().get(PcrNoticeType.tree.name()));
                subject.sendMessage(tree);
            }
            if (ats.containsKey(PcrNoticeType.order.name())) {
                Message order = makeAts(ats.get(PcrNoticeType.order.name()), subject)
                        .plus("\n" + result.getReturnObject().get(PcrNoticeType.order.name()));
                subject.sendMessage(order);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new At(sender).plus("指令错误." + txt);
        }
    }


}
