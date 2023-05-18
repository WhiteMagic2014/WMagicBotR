package com.whitemagic2014.command.impl.everywhere.remind;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.everywhere.BaseEveryWhereCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.service.RemindService;
import com.whitemagic2014.util.DateFormatUtil;
import com.whitemagic2014.util.MagicHelper;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 预约功能
 * @author: magic chen
 * @date: 2021/1/7 17:15
 **/
@Command
public class RemindCommand extends BaseEveryWhereCommand {

    @Autowired
    RemindService service;

    private List<String> okPool = Arrays.asList("好的", "ok", "知道了");
    // 燃烧吧！我的中二之魂
    private List<String> dmailPool = Arrays.asList("入侵sern失败", "显像管电视未启动", "启动电话微波炉失败", "Dmail发送失败", "时间设置错误哦", "改变世界线过于危险,Dmail已被Lab禁止");

    private String getOk() {
        return okPool.get(MagicHelper.randomInt(okPool.size()));
    }

    private String getDmail() {
        return dmailPool.get(MagicHelper.randomInt(dmailPool.size()));
    }


    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        if (args.size() != 2) {
            return new PlainText("格式:\n备忘 yyyy-MM-dd/HH:mm:ss [备忘内容]\n备忘 HH:mm:ss后 [备忘内容]");
        }
        String param = args.get(0);
        String taskKey = "";
        Date date;
        if (param.contains("后")) {
            String time = param.replace("后", "");
            String[] hms = time.split(":");
            int lastIndex = hms.length - 1;
            int result = 0;
            for (int i = lastIndex; i >= 0; i--) {
                result += Integer.parseInt(hms[i]) * Math.pow(60, (lastIndex - i));
            }
            date = DateFormatUtil.dateAdd(new Date(), (long) result, TimeUnit.SECONDS);
        } else {
            try {
                date = DateFormatUtil.sdfv3.parse(param);
            } catch (ParseException pe) {
                return new PlainText("时间格式错误 yyyy-MM-dd/HH:mm:ss");
            }
            if (date.before(new Date())) {
                return new PlainText(getDmail());
            }
        }
        if (subject instanceof Group) {
            taskKey = service.groupRemind(subject.getId(), sender.getId(), args.get(1), date);
        } else if (subject instanceof User) {
            taskKey = service.friendRemind(sender.getId(), args.get(1), date);
        }
        return new PlainText(getOk() + ",备忘id=" + taskKey);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("备忘");
    }
}
