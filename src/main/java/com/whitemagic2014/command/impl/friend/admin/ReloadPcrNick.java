package com.whitemagic2014.command.impl.friend.admin;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.friend.AdminFriendCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.Pcrjjc;
import com.whitemagic2014.vo.PrivateModel;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @Description: 重新加载 nickname 文件（不下载新的文件） 适合手动修改文件后刷新缓存
 * @author: magic chen
 * @date: 2021/1/27 15:49
 **/
@Command
public class ReloadPcrNick extends AdminFriendCommand {

    @Autowired
    Pcrjjc pcrjjc;

    @Override
    protected Message executeHandle(Friend sender, ArrayList<String> args, MessageChain messageChain, Friend subject) {
        PrivateModel<String> result = pcrjjc.reloadNameFile();
        return new PlainText(result.getReturnObject());
    }


    @Override
    public CommandProperties properties() {
        return new CommandProperties("重载nickname");
    }
}
