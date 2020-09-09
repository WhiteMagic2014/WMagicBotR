package com.whitemagic2014.command.impl.group.pcrjjc;

import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcrjjc.Answer;
import com.whitemagic2014.pojo.pcrjjc.TeamMember;
import com.whitemagic2014.service.Pcrjjc;
import com.whitemagic2014.util.MagicHelper;
import com.whitemagic2014.util.MagicMaps;
import com.whitemagic2014.util.Path;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 返回图形的jjc查询
 * 效率不高 用于私聊 单条消息图片过多会出错 不继承pcrNoAuthCommand 那个需要开关开启
 * 后期没有解决效率问题 这个功能可能会直接砍掉
 * @author: magic chen
 * @date: 2020/8/29 15:28
 **/
@Component
public class PcrAskJJCImage extends NoAuthCommand {

    @Autowired
    Pcrjjc pcrjjc;

    private Map<Integer, BufferedImage> pcrimage = new HashMap();

    private static final Logger logger = LoggerFactory.getLogger(PcrAskJJCImage.class);


    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {

        PrivateModel<List<Answer>> model = pcrjjc.checkjjc(args, 2);

        if (!model.isSuccess()) {
            return simpleErrMsg(sender, model);
        }
        List<Answer> answers = model.getReturnObject();

        MessageChain result = new PlainText("查询结果:\n").plus("");
        for (Answer answer : answers) {
            try {
                MessageChain temp = id2Image(answer.getAtk(), subject).plus("\n");
                result = result.plus(temp);
            } catch (Exception e) {
                logger.error("查询出错",e);
                return new PlainText("查询出错");
            }
        }
        return result;


    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("测试查");
    }


    protected MessageChain id2Image(List<TeamMember> members, Contact subject) throws Exception {
        MessageChain chain = MessageUtils.newChain();
        for (TeamMember member : members) {
            chain = chain.plus(subject.uploadImage(member2Image(member)));
        }
        return chain;
    }


    /**
     * @Name: member2Image
     * @Description: 将pcr member 综合 星级 专武 转头像
     * @Param: member
     * @Return: java.awt.image.BufferedImage
     * @Author: magic chen
     * @Date: 2020/8/29 13:43
     **/
    private BufferedImage member2Image(TeamMember member) throws Exception {

        Integer id = member.getId();
        Integer star = member.getStar();
        Boolean equip = member.getEquip();

        String key = "pcrJjcImageDetail_" + id + star + equip;
        // 读缓存
        if (MagicMaps.check(key)) return MagicMaps.get(key, BufferedImage.class);

        int realid;
        if (star < 3) {
            realid = id + 10;
        } else if (star >= 3 && star < 6) {
            realid = id + 30;
        } else {
            realid = id + 60;
        }
        // 暂时没有把星级 和 专武 图标合成到头像上。

        BufferedImage result = getPcrImageInternal(realid);
        // 这里的缓存需要有过期,因为是比较定制化的图片 不需要缓存太久 1小时
        MagicMaps.putWithExpire(key, result, 1L, TimeUnit.HOURS);
        return result;
    }


    /**
     * @Name: getPcrImage
     * @Description: 根据pcr角色id 头像
     * @Param: id
     * @Return: java.awt.image.BufferedImage
     * @Author: magic chen
     * @Date: 2020/8/29 13:40
     **/
    private BufferedImage getPcrImageInternal(Integer id) throws Exception {
        if (pcrimage.containsKey(id)) return pcrimage.get(id);
        String path = Path.getPath() + "PcrRoleImage/" + id + ".jpeg";
        BufferedImage image = ImageIO.read(new FileInputStream(path));
        BufferedImage small = MagicHelper.resizeBufferedImage(image, 64, 64, true);
        pcrimage.put(id, small);
        return small;
    }


}
