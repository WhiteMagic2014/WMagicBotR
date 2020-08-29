package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcr.Notice;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 查询预约boss
 * @author: magic chen
 * @date: 2020/8/24 22:19
 **/
@Component
public class CheckOrder extends PcrNoAuthCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("查预约", "查预定");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<Map<Integer, List<Notice>>> result = pcrBotService.checkOrder(subject.getId());

        if (!result.isSuccess()) {
            return simpleErrMsg(sender, result);
        }

        Map<Integer, List<Notice>> dataMap = result.getReturnObject();
        MessageChain msg = MessageUtils.newChain("预约情况\n");
        for (Integer num : dataMap.keySet()) {
            msg = msg.plus(num + "号boss:\n");
            List<Long> uids = dataMap.get(num).stream().map(Notice::getUid).collect(Collectors.toList());
            msg = msg.plus(makeAts(uids, subject)).plus("\n");
        }
        return msg;
    }


}
