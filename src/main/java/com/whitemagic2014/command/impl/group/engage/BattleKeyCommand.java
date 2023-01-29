package com.whitemagic2014.command.impl.group.engage;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.dao.EngageBattleDao;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.EngageBattle;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 火纹续战分享码
 * @author: magic chen
 * @date: 2023/1/29 14:40
 **/
@Command
public class BattleKeyCommand extends NoAuthCommand {


    @Autowired
    EngageBattleDao dao;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("火纹续战");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {


        String com = args.get(0);
        try {


            if ("登记".equals(com)) {
                String key = args.get(1).toLowerCase();
                String remark = args.get(2);
                EngageBattle battle = new EngageBattle();
                battle.setBattleKey(key);
                battle.setRemark(remark);
                battle.setStatus(1);
                dao.insert(battle);
                return new PlainText("登记成功");

            } else if ("结束".equals(com)) {
                String key = args.get(1).toLowerCase();
                dao.updateStatusByKey(key, 0);
                return new PlainText("结束成功");
            } else if ("认领".equals(com)) {
                String key = args.get(1).toLowerCase();
                dao.updateStatusByKey(key, 2);
                return new PlainText("认领成功");
            } else if ("交接".equals(com)) {
                String key = args.get(1).toLowerCase();
                dao.updateStatusByKey(key, 1);
                return new PlainText("交接成功");
            } else if ("帮助".equals(com)) {
                return new PlainText(help());
            } else if ("查询".equals(com)) {
                List<EngageBattle> list = dao.listAllAbleBattle();
                if (list.isEmpty()) {
                    return new PlainText("当前没有可用续战可以挑战");
                }
                String result = "当前可用续战:\n";
                for (EngageBattle b : list) {
                    result += b.getBattleKey() + " 备注：" + b.getRemark() + "\n";
                }
                return new PlainText(result);
            }
        } catch (Exception e) {

            e.printStackTrace();

            return new PlainText(help());
        }

        return null;
    }


    private String help() {
        return "续战查询功能:\n"
                + "查询可用续战: 火纹续战 查询\n"
                + "共享一个续战: 火纹续战 登记 abcd12 困难森林\n"
                + "关闭一个续战: 火纹续战 结束 abcd12\n"
                + "认领一个续战开始战斗: 火纹续战 认领 abcd12\n"
                + "打完认领的续战交接: 火纹续战 交接 abcd12";
    }

}
