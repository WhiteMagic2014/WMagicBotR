package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrAdminCommand;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 直接修改boss状态
 * @author: magic chen
 * @date: 2020/8/28 16:39
 **/
@Component
public class UpdateBoss extends PcrAdminCommand {

    String txt = "修改状态 周目 几王 血量";

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        if (args.isEmpty() || args.size() > 3) return new At(sender).plus("指令错误," + txt);
        try {
            Integer cycle = Integer.valueOf(args.get(0));
            Integer num = Integer.valueOf(args.get(1));
            Long hpnow = null;
            if (args.size() == 3) {
                hpnow = Long.valueOf(args.get(2));
            }
            return simpleMsg(sender, pcrBotService.updateBossState(subject.getId(), sender.getId(), cycle, num, hpnow));

        } catch (Exception e) {
            return new At(sender).plus("指令错误," + txt);
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("修改状态");
    }
}
