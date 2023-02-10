package com.whitemagic2014.command.impl.everywhere.engage;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.EngageBattle;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
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
        return new CommandProperties("续战交接", "交接");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        try {
            String key = args.get(0).toLowerCase();

            EngageBattle battle = new EngageBattle();
            battle.setBattleKey(key);
            battle.setStatus(1);
            dao.updateByKey(battle);

            return new PlainText("交接成功");
        } catch (Exception e) {
            return new PlainText(help());
        }
    }
}
