package com.whitemagic2014.command.impl.group.currency;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.UserCoinService;
import com.whitemagic2014.vo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @Description: 货币系统开户
 * @author: magic chen
 * @date: 2020/12/3 10:53
 **/
@Command
public class OpenCoinAccountCommand extends NoAuthCommand {

    @Autowired
    UserCoinService ucs;

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        PrivateModel<String> result = ucs.createAccount(sender.getId(), subject.getId());
        return simpleMsg(sender, result);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("开户", "开通账户");
    }
}
