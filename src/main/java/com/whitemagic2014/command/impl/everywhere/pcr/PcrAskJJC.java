package com.whitemagic2014.command.impl.everywhere.pcr;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.annotate.Switch;
import com.whitemagic2014.command.impl.everywhere.BaseEveryWhereCommand;
import com.whitemagic2014.dic.Dic;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.pcrjjc.Answer;
import com.whitemagic2014.pojo.pcrjjc.TeamMember;
import com.whitemagic2014.service.Pcrjjc;
import com.whitemagic2014.vo.PrivateModel;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: jjc查询 默认国服
 * @author: magic chen
 * @date: 2020/8/26 17:24
 **/
@Command
@Switch(name = Dic.Component_Pcr_JJC)
public class PcrAskJJC extends BaseEveryWhereCommand {

    @Autowired
    Pcrjjc pcrjjc;

    private static final Logger logger = LoggerFactory.getLogger(PcrAskJJC.class);


    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        PrivateModel<List<Answer>> model = pcrjjc.checkjjc(args, 2);

        if (!model.isSuccess()) {
//            return simpleErrMsg(sender, model);
            logger.error(model.getReturnMessage());
            return null;
        }
        List<Answer> answers = model.getReturnObject();

        String result = "查询结果:\n";
        int i = 1;
        for (Answer answer : answers) {
            String at = i + ".";
            for (TeamMember member : answer.getAtk()) {
                String mt = member.getName() + member.getStar() + "";
                if (member.getEquip()) {
                    mt += "带专";
                }
                at += "[" + mt + "] ";
            }
            result += at + answer.getUp() + "赞 " + answer.getDown() + "踩\n";
            i++;
        }
        result = result.substring(0, result.length() - 1);
        return simpleMsg(sender, new PlainText(result));
    }


    @Override
    public CommandProperties properties() {
        return new CommandProperties("jjc文字");
    }


}
