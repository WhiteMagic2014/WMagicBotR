package com.whitemagic2014.command.impl.everywhere;

import com.whitemagic2014.command.EverywhereCommand;
import com.whitemagic2014.dao.CanEatDao;
import com.whitemagic2014.pojo.CanEat;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 能不能吃啥
 * @ps: 作者本人有结石, 很多东西忌口不能吃, 但是又记不住 私人定制
 * @author: magic chen
 * @date: 2020/8/21 18:27
 **/
@Component
public class CanEatCommand extends BaseEveryWhereCommand {

    @Autowired
    CanEatDao ced;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("能吃", "可以吃", "吃");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (sender.getId() != 418379149L) {
            return null;
        }
        if (args.get(0).equals("记录") || args.get(0).equals("创建")) {
            List<CanEat> ceList = ced.findByName(args.get(1).trim());
            if (!ceList.isEmpty()) {
                return simpleMsg(sender, new PlainText("已经记录"));
            }
            CanEat ce = new CanEat();
            ce.setItemName(args.get(1).trim());
            if (args.get(2).contains("不")) {
                ce.setCan(false);
            } else {
                ce.setCan(true);
            }
            ced.insert(ce);
            return simpleMsg(sender, new PlainText("已经记录"));
        } else {
            List<CanEat> ceList = ced.findByName(args.get(0).trim());
            if (ceList.isEmpty()) {
                return simpleMsg(sender, new PlainText("还没记录 " + args.get(0).trim() + " 能不能吃 百度之后记得记录一下"));
            }
            if (ceList.get(0).getCan()) {
                return simpleMsg(sender, new PlainText("能吃"));
            } else {
                return simpleMsg(sender, new PlainText("不能吃"));
            }
        }
    }


}
