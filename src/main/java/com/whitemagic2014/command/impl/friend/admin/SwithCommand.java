package com.whitemagic2014.command.impl.friend.admin;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.friend.AdminFriendCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.util.MagicSwitch;
import com.whitemagic2014.vo.PrivateModel;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;

/**
 * @Description: 动态组件开关
 * @author: magic chen
 * @date: 2020/8/27 17:58
 **/
@Command
public class SwithCommand extends AdminFriendCommand {


    @Override
    protected Message executeHandle(Friend sender, ArrayList<String> args, MessageChain messageChain, Friend subject) {
        if (args.size() != 2) {
            return new PlainText("指令错误: 开关 [开启/关闭] [组件名]\n当前组件有:\n"+MagicSwitch.list());
        }
        String opt = args.get(0);
        String componentName = args.get(1);

        PrivateModel<String> openResult;
        if ("开启".equals(opt)) {
            openResult = MagicSwitch.open(componentName);
        } else if ("关闭".equals(opt)) {
            openResult = MagicSwitch.close(componentName);
        } else {
            return new PlainText("指令错误: 开关 [开启/关闭] [组件名]\n当前组件有:\n"+MagicSwitch.list());
        }
        return simpleMsg(openResult);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("开关");
    }
}
