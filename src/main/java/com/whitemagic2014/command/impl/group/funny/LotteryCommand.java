package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Description: 彩票
 * @author: magic chen
 * @date: 2022/10/25 15:24
 **/
@Command
public class LotteryCommand extends NoAuthCommand {

    @Override
    public CommandProperties properties() {
        return new CommandProperties("彩票");
    }


    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        return new PlainText(red().stream().map(i -> i + " ").reduce("红:", String::concat) + "\n蓝:" + bule());
    }


    private List<Integer> red() {
        Random random = new Random();
        List<Integer> pool = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 33; i++) {
            pool.add(i);
        }
        for (int i = 1; i <= 6; i++) {
            int flag = random.nextInt(pool.size());
            int redtemp = pool.get(flag);
            result.add(redtemp);
            pool.remove(flag);
        }
        return result.stream().sorted(Integer::compareTo).collect(Collectors.toList());
    }

    private Integer bule() {
        Random random = new Random();
        return (random.nextInt(16) + 1);
    }
}
