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
import java.util.List;

/**
 * @Description: 续战查询
 * @author: magic chen
 * @date: 2023/2/1 10:05
 **/
@Command
public class CheckKey extends BattleKeyCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("续战查询", "查询续战");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        try {
            List<EngageBattle> list = dao.listAllAbleBattle();
            if (list.isEmpty()) {
                return new PlainText("当前没有可用续战可以挑战");
            }

            String qq = String.valueOf(sender.getId());


            String result = "为了更方便大家使用,打完的续战要及时关闭哦\n当前可用续战(●=发起,○=已参加):\n";
            for (EngageBattle b : list) {
                String tag = "";
                if (qq.equals(b.getQqNum())) {
                    tag = "●";
                } else if (qq.equals(b.getLinkNum1())
                        || qq.equals(b.getLinkNum2())
                        || qq.equals(b.getLinkNum3())
                        || qq.equals(b.getLinkNum4())) {
                    tag = "○";
                }
                result += b.getBattleKey().toUpperCase() + "  " + tag + "  备注 ：" + b.getRemark() + "\n";
            }
            return new PlainText(result);
        } catch (Exception e) {
            return new PlainText(help());
        }
    }

}
