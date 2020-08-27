package com.whitemagic2014.command.impl.everywhere.pcr;

import com.whitemagic2014.command.EverywhereCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcrjjc.Answer;
import com.whitemagic2014.pojo.pcrjjc.TeamMember;
import com.whitemagic2014.service.Pcrjjc;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: jjc查询 默认国服
 * @author: magic chen
 * @date: 2020/8/26 17:24
 **/
@Component
public class PcrAskJJC implements EverywhereCommand {

    @Autowired
    Pcrjjc pcrjjc;


    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        PrivateModel<List<Answer>> model = pcrjjc.checkjjc(args, 2);

        if (!model.isSuccess()) {
            if (sender instanceof Member) {
                return new At((Member) sender).plus(model.getReturnMessage());
            } else {
                return new PlainText(model.getReturnMessage());
            }
        }
        List<Answer> answers = model.getReturnObject();
        String result = "查询结果:\n";
        for (Answer answer : answers) {
            String at = "";
            for (TeamMember member : answer.getAtk()) {
                String mt = member.getName() + member.getStar() + "星";
                if (member.getEquip()) mt += "带专";
                at += "[" + mt + "] ";
            }
            result += at + answer.getUp() + "赞 " + answer.getDown() + "踩\n";
        }
        result = result.substring(0, result.length() - 1);
        if (sender instanceof Member) {
            return new At((Member) sender).plus(result);
        } else {
            return new PlainText(result);
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("jjc查询");
    }
}
