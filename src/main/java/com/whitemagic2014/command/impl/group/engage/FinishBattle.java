package com.whitemagic2014.command.impl.group.engage;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;

/**
 * @Description: 续战交接
 * @author: magic chen
 * @date: 2023/2/1 10:29
 **/
@Command
public class FinishBattle extends BattleKeyCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("续战交接");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        try {
            String key = args.get(0).toLowerCase();
            dao.updateStatusByKey(key, 1);
            return new PlainText("交接成功");
        } catch (Exception e) {
            return new PlainText(help());
        }
    }
}
