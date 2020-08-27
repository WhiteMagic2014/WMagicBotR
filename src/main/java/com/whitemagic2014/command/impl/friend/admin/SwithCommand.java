package com.whitemagic2014.command.impl.friend.admin;

import com.whitemagic2014.command.impl.friend.AdminFriendCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.util.MagicSwitch;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 动态组件开关
 * @author: magic chen
 * @date: 2020/8/27 17:58
 **/
@Component
public class SwithCommand extends AdminFriendCommand {

    @Autowired
    MagicSwitch ms;

    @Override
    protected Message executeHandle(Friend sender, ArrayList<String> args, MessageChain messageChain, Friend subject) {
        if (args.size() != 2) return new PlainText("指令错误: 开关 [开启/关闭] [组件名]");
        String opt = args.get(0);
        String componentName = args.get(1);

        PrivateModel<String> openResult;
        if (opt.equals("开启")) {
            openResult = ms.open(componentName);
        } else if (opt.equals("关闭")) {
            openResult = ms.close(componentName);
        } else {
            return new PlainText("指令错误: 开关 [开启/关闭] [组件名]");
        }
        return simpleMsg(openResult);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("开关");
    }
}
