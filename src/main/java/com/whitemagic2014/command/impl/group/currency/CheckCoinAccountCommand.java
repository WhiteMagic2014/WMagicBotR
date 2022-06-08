package com.whitemagic2014.command.impl.group.currency;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.currency.CoinLog;
import com.whitemagic2014.pojo.currency.UserCoin;
import com.whitemagic2014.service.UserCoinService;
import com.whitemagic2014.vo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 描述这个类的作用
 * @author: magic chen
 * @date: 2020/12/3 11:09
 **/
@Command
public class CheckCoinAccountCommand extends NoAuthCommand {

    @Autowired
    UserCoinService ucs;

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        PrivateModel<List<CoinLog>> result = ucs.checkLog(sender.getId(), null, null);
        if (!result.isSuccess()){
            return simpleErrMsg(sender,result);
        }
        List<CoinLog> logList = result.getReturnObject();
        String msg =  "\n" +logList.stream().map(CoinLog::getRemark).map(s->s.concat("\n")).reduce("",String::concat);
        return new At(sender.getId()).plus(msg);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("查账", "查询账户记录");
    }
}
