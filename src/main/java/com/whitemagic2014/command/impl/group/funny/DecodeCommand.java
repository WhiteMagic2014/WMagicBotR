package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.util.codec.MagicEncode;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 朝代解密
 * @author: magic chen
 * @date: 2020/8/21 18:21
 **/
@Component
public class DecodeCommand extends NoAuthCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("解密", "帮我解密");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        At at = new At(sender);
        String result = "已经解密完啦,明文: \n" + MagicEncode.decode(args.get(0), null);
        return at.plus(result);
    }

   
}
