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
 * @Description: 续战登记
 * @author: magic chen
 * @date: 2023/2/1 10:08
 **/
@Command
public class RegistKey extends BattleKeyCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("续战登记", "登记续战");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        try {
            String key = args.get(0).toLowerCase();
            String remark = "";
            if (args.size() > 1) {
                remark = args.get(1);
            }
            EngageBattle origin = dao.selectByKey(key);
            if (origin == null) {
                EngageBattle battle = new EngageBattle();
                battle.setBattleKey(key);
                battle.setRemark(remark);
                battle.setStatus(1);
                battle.setQqNum(String.valueOf(sender.getId()));
                dao.insert(battle);
            } else {
                EngageBattle update = new EngageBattle();
                update.setBattleKey(key);
                update.setRemark(remark);
                update.setStatus(1);
                update.setQqNum(String.valueOf(sender.getId()));
                update.setLinkNum1("");
                update.setLinkNum2("");
                update.setLinkNum3("");
                update.setLinkNum4("");
                update.setFinNum("");
                dao.updateByKey(update);
            }
            return new PlainText("登记成功");
        } catch (Exception e) {
            return new PlainText(help());
        }
    }
}
