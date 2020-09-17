package com.whitemagic2014.command.impl.everywhere.pcr;

import com.whitemagic2014.command.impl.everywhere.BaseEveryWhereCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcrjjc.Answer;
import com.whitemagic2014.pojo.pcrjjc.TeamMember;
import com.whitemagic2014.service.Pcrjjc;
import com.whitemagic2014.util.MagicImage;
import com.whitemagic2014.util.MagicMaps;
import com.whitemagic2014.util.Path;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
public class PcrAskJJCImage extends BaseEveryWhereCommand {


    private static HashMap<String, BufferedImage> icon = new HashMap<>();

    private static Map<Integer, BufferedImage> pcrimage = new HashMap();


    @Autowired
    Pcrjjc pcrjjc;

    private static final Logger logger = LoggerFactory.getLogger(PcrAskJJCImage.class);


    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {

        PrivateModel<List<Answer>> model = pcrjjc.checkjjc(args, 2);

        if (!model.isSuccess()) {
            return simpleErrMsg(sender, model);
        }
        List<Answer> answers = model.getReturnObject();
        if (answers.isEmpty()) return simpleMsg(sender, new PlainText("没有找到解法"));

        String zuoyeKey = "Image_" + model.getReturnMessage();

        BufferedImage zuoye;
        if (MagicMaps.check(zuoyeKey)) {
            zuoye = MagicMaps.get(zuoyeKey, BufferedImage.class);
        } else {
            // 创建新的作业
            zuoye = answer2Image(answers);
            MagicMaps.putWithExpire(zuoyeKey, zuoye, 1L, TimeUnit.HOURS);
        }

        return simpleMsg(sender, new PlainText("查询结果:\n").plus(subject.uploadImage(zuoye)));
    }


    @Override
    public CommandProperties properties() {
        return new CommandProperties("测试查");
    }


    /**
     * @Name: answer2Image
     * @Description: 作业json 转 image
     * @Param: list
     * @Return: java.awt.image.BufferedImage
     * @Author: magic chen
     * @Date: 2020/9/16 16:18
     **/
    private static BufferedImage answer2Image(List<Answer> answerList) throws Exception {
        BufferedImage temp = new BufferedImage(64 * 5 + 64, 65 * answerList.size(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = temp.createGraphics();
        graphics.setColor(new Color(255, 255, 255));
        //填充背景白色
        graphics.fillRect(0, 0, temp.getWidth(), temp.getHeight());

        for (int i = 0; i < answerList.size(); i++) {
            Answer an = answerList.get(i);
            List<TeamMember> atk = an.getAtk();
            for (int j = 0; j < atk.size(); j++) {
                BufferedImage roleTemp = member2Image(atk.get(j));
                graphics.drawImage(roleTemp, (1 + roleTemp.getWidth()) * j, (1 + roleTemp.getWidth()) * i, roleTemp.getWidth(), roleTemp.getHeight(), null);
                if (j == 4) {
                    graphics.setColor(new Color(0, 0, 0));
                    graphics.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                    graphics.drawString("↑ " + an.getUp(), (1 + roleTemp.getWidth()) * (j + 1), (1 + roleTemp.getWidth()) * i + 37);
                    graphics.drawString("↓ " + an.getDown(), (1 + roleTemp.getWidth()) * (j + 1), (1 + roleTemp.getWidth()) * i + 55);
                }
            }
        }
        return temp;
    }


    /**
     * @Name: member2Image
     * @Description: 将member 对象 转换成 对应的 角色头像
     * @Param: member
     * @Return: java.awt.image.BufferedImage
     * @Author: magic chen
     * @Date: 2020/9/16 15:54
     **/
    private static BufferedImage member2Image(TeamMember member) throws Exception {

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
        BufferedImage origin = getPcrImageInternal(realid);
        BufferedImage equipIcon = getEquip();
        BufferedImage starIcon = getStar();
        BufferedImage grayStarIcon = getGrayStar();
        BufferedImage redStarIcon = getRedStar();

        BufferedImage temp = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = temp.createGraphics();
        // 头像
        graphics.drawImage(origin, 0, 0, origin.getWidth(), origin.getHeight(), null);
        // 专武
        if (equip) {
            graphics.drawImage(equipIcon, 2, 2, equipIcon.getWidth(), equipIcon.getHeight(), null);
        }
        // 星级
        int y = 64 - 10 - 2; // 距离底边距离
        int dx = 0;// x的偏移量
        int width = grayStarIcon.getWidth();
        int height = grayStarIcon.getHeight();
        switch (star) {
            case 1:
                graphics.drawImage(starIcon, dx + width * 0, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 1, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 2, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 3, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 4, y, width, height, null);
                break;

            case 2:
                graphics.drawImage(starIcon, dx + width * 0, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 1, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 2, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 3, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 4, y, width, height, null);
                break;

            case 3:
                graphics.drawImage(starIcon, dx + width * 0, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 1, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 2, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 3, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 4, y, width, height, null);
                break;

            case 4:
                graphics.drawImage(starIcon, dx + width * 0, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 1, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 2, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 3, y, width, height, null);
                graphics.drawImage(grayStarIcon, dx + width * 4, y, width, height, null);
                break;

            case 5:
                graphics.drawImage(starIcon, dx + width * 0, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 1, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 2, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 3, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 4, y, width, height, null);
                break;

            case 6:
                graphics.drawImage(starIcon, dx + width * 0, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 1, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 2, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 3, y, width, height, null);
                graphics.drawImage(starIcon, dx + width * 4, y, width, height, null);
                graphics.drawImage(redStarIcon, dx + width * 5, y, width, height, null);
                break;
        }
        graphics.dispose();

        // 这里的缓存需要有过期,因为是比较定制化的图片 不需要缓存太久 1小时
        MagicMaps.putWithExpire(key, temp, 1L, TimeUnit.HOURS);
        return temp;
    }


    /**
     * @Name: cutIcon
     * @Description: 如果没切过 icon 就切一下图
     * @Param:
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/9/16 11:56
     **/
    private static void cutIcon() {
        if (icon.isEmpty()) {
            try {
                String path = Path.getPath() + "PcrRoleImage/charaMarks.png";
                BufferedImage image = ImageIO.read(new FileInputStream(path));

                BufferedImage equip = MagicImage.cut(0, 0, 16, 16, image);
                BufferedImage star = MagicImage.cut(16, 0, 16, 16, image);
                BufferedImage grayStar = MagicImage.cut(0, 16, 16, 16, image);
                BufferedImage redStar = MagicImage.cut(16, 16, 16, 16, image);

                icon.put("equip", equip);
                icon.put("star", MagicImage.resizeBufferedImage(star, 10, 10, true));
                icon.put("grayStar", MagicImage.resizeBufferedImage(grayStar, 10, 10, true));
                icon.put("redStar", MagicImage.resizeBufferedImage(redStar, 10, 10, true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static BufferedImage getEquip() {
        if (icon.isEmpty()) {
            cutIcon();
        }
        return icon.get("equip");
    }


    private static BufferedImage getStar() {
        if (icon.isEmpty()) {
            cutIcon();
        }
        return icon.get("star");
    }

    private static BufferedImage getGrayStar() {
        if (icon.isEmpty()) {
            cutIcon();
        }
        return icon.get("grayStar");
    }

    private static BufferedImage getRedStar() {
        if (icon.isEmpty()) {
            cutIcon();
        }
        return icon.get("redStar");
    }


    /**
     * @Name: getPcrImage
     * @Description: 根据pcr角色id 头像
     * @Param: id
     * @Return: java.awt.image.BufferedImage
     * @Author: magic chen
     * @Date: 2020/8/29 13:40
     **/
    private static BufferedImage getPcrImageInternal(Integer id) throws Exception {
        if (pcrimage.containsKey(id)) return pcrimage.get(id);
        String path = Path.getPath() + "PcrRoleImage/" + id + ".jpeg";

        File file = new File(path);
        // 6星头像不存在 向下兼容取3星
        if (!file.exists()) {
            if (id % 100 == 61) {
                path = Path.getPath() + "PcrRoleImage/" + (id - 30) + ".jpeg";
                logger.warn(id + ",6星头像不存在 向下兼容取3星");
            } else {
                logger.warn(id + ",头像不存在,请添加头像资源");
            }
        }
        BufferedImage image = ImageIO.read(new FileInputStream(path));
        BufferedImage small = MagicImage.resizeBufferedImage(image, 64, 64, true);
        pcrimage.put(id, small);
        return small;
    }


}
