package com.whitemagic2014.command.impl.everywhere.pcr;

import com.whitemagic2014.command.impl.everywhere.BaseEveryWhereCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.Pcrjjc;
import com.whitemagic2014.vo.PrivateModel;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: check pcrd错误码
 * @author: magic chen
 * @date: 2020/11/4 10:58
 **/
@Component
public class CheckPcrdfansCode extends BaseEveryWhereCommand {

    @Autowired
    Pcrjjc pcrjjc;

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        Integer code;
        try {
            code = Integer.valueOf(args.get(0).trim());
        } catch (NumberFormatException nfe) {
            return simpleMsg(sender, new PlainText(args.get(0) + " : Unknown"));
        }
        PrivateModel<String> check = pcrjjc.checkPcrdfansCode(code);
        return simpleMsg(sender, new PlainText(args.get(0) + " : " + check.getReturnMessage()));
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("pcrdcode");
    }
}
