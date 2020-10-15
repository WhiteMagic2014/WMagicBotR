package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.util.MagicGacha;
import com.whitemagic2014.util.MagicMd5;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 每日抽签
 * @author: magic chen
 * @date: 2020/8/21 15:57
 **/
@Component
public class DailyLuckCommand extends NoAuthCommand {

    MagicGacha dailyGacha;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DailyLuckCommand() {
        Map<String, Integer> itemAndRate = new HashMap<>();
        itemAndRate.put("大吉 恭喜你! 万事如意 百无禁忌 不去买个彩票吗?欧皇 ", 500);
        itemAndRate.put("中吉 恭喜拉! 抽卡应该能见彩咯~ ", 1500);
        itemAndRate.put("小吉 恭喜！刷困难本也许能出3碎片 走起！", 1500);
        itemAndRate.put("吉 小小的幸运 留意脚下 没准捡到硬币了呢~", 2500);
        itemAndRate.put("末吉 平平淡淡才是真 健康快乐每一天", 2000);
        itemAndRate.put("凶 风水轮流转 没准明天就欧了呢?", 1500);
        itemAndRate.put("大凶 啊这...给你换一个吧...\n恭喜你 抽到了特殊签! 姬吉!!!", 500);
        dailyGacha = new MagicGacha(itemAndRate);
    }


    @Override
    public CommandProperties properties() {
        return new CommandProperties("gacha", "抽签");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        String seed = sender.getId() + sdf.format(new Date());
        String item = dailyGacha.gacha(MagicMd5.getGachaRate(seed));

        At at = new At(sender);
        PlainText plainText = new PlainText("\n抽到了 " + item);
        return at.plus(plainText);
    }

}
