package com.whitemagic2014.command.impl.group.funny;

import com.github.WhiteMagic2014.gptApi.Chat.CreateChatCompletionRequest;
import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.annotate.Switch;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.dic.Dic;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.RemindService;
import com.whitemagic2014.util.DateFormatUtil;
import com.whitemagic2014.util.MagicHelper;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @Description: 预约功能v2 使用gpt解析自然语言,需要开启gpt使用
 * @author: magic chen
 * @date: 2023/4/17 10:41
 **/
@Command
@Switch(name = Dic.Component_ChatGPT)
public class RemindCommandV2 extends NoAuthCommand {

    @Autowired
    RemindService service;

    @Value("${ChatGPT.key}")
    private String key;


    private List<String> okPool = Arrays.asList("好的", "ok", "知道了");

    private String getOk() {
        return okPool.get(MagicHelper.randomInt(okPool.size()));
    }

    public CommandProperties properties() {
        return new CommandProperties("备忘V2","备忘2");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        Date now = new Date();

        String prompt = args.stream().map(s -> {
            return s.concat(" ");
        }).reduce("", String::concat);

        // 构造初始请求
        CreateChatCompletionRequest request = new CreateChatCompletionRequest()
                .key(key)
                .maxTokens(500)
                .addMessage("system", "当前时间为2023年04月13日11:26:02,请将给出的内容归纳为以下格式: 备忘 内容 yyyy-MM-dd/HH:mm:ss")
                .addMessage("user", "3天后早上8点提醒我去机场接我的朋友ammy")
                .addMessage("assistant", "备忘 2023-04-16/08:00:00 记得去机场接你的朋友ammy")
                .addMessage("user", "2023年05月10日是peter的生日，记得提前提醒我为他准备生日礼物")
                .addMessage("assistant", "备忘 2023-05-07/00:00:00 距离peter的生日只剩下3天了，记得准备好生日礼物。")
                .addMessage("user", "现在当前时间变更为" + DateFormatUtil.sdfv4.format(now) + ";" + prompt);
        String result = "";
        try {
            result = request.sendForChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            return new PlainText("很抱歉，出错了");
        }
        // gpt解析结果不符
        if (!result.startsWith("备忘 ")) {
            return new PlainText("解析失败：" + result);
        }
        // 处理指令
        String[] split = result.replaceFirst("备忘 ", "").split(" ");
        String dateStr = split[0];
        String msg = split[1];
        Date date;
        try {
            date = DateFormatUtil.sdfv3.parse(dateStr);
        } catch (ParseException pe) {
            return new PlainText("解析失败：" + result);
        }
        if (date.before(now)) {
            return new PlainText("解析失败：" + result);
        }
        String taskKey = service.groupRemind(subject.getId(), sender.getId(), msg, date);
        return new PlainText(getOk() + ",已经创建" + result + ",备忘id=" + taskKey);
    }
}
