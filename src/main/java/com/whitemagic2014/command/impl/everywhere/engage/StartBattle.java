package com.whitemagic2014.command.impl.everywhere.engage;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.EngageBattle;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @Description: 续战认领
 * @author: magic chen
 * @date: 2023/2/1 10:28
 **/
@Command
public class StartBattle extends BattleKeyCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("续战认领", "认领");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        try {
            String key = args.get(0).toLowerCase();

            EngageBattle battle = dao.selectByKey(key);
            if (battle == null) {
                return new PlainText("认领成功");
            }

            String qq = String.valueOf(sender.getId());

            battle.setStatus(2);
            if (StringUtils.isBlank(battle.getLinkNum1())) {
                battle.setLinkNum1(qq);
            } else if (StringUtils.isBlank(battle.getLinkNum2())) {
                battle.setLinkNum2(qq);
            } else if (StringUtils.isBlank(battle.getLinkNum3())) {
                battle.setLinkNum3(qq);
            } else {
                battle.setLinkNum4(qq);
            }
            dao.updateByKey(battle);

            return new PlainText("认领成功");
        } catch (Exception e) {
            return new PlainText(help());
        }
    }
}
