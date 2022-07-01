package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.annotate.Command;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.util.ChaosImage;
import com.whitemagic2014.util.MagicImage;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

/**
 * @Description: 图片混沌加密
 * @author: magic chen
 * @date: 2022/7/1 11:44
 **/
@Command
public class ChaosImageCommand extends NoAuthCommand {


    @Override
    public CommandProperties properties() {
        return new CommandProperties("混沌");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        Image image = messageChain.get(Image.Key);
        if (image == null) {
            return null;
        }
        String urlStr = Mirai.getInstance().queryImageUrl(sender.getBot(), image);
        try {
            BufferedImage im = ImageIO.read(new URL(urlStr));
            String com = args.get(0);
            String pwd = args.get(1);
            BufferedImage de = null;
            if ("加密".equals(com)) {
                de = ChaosImage.process(im, Integer.valueOf(pwd), true);
            } else if ("解密".equals(com)) {
                de = ChaosImage.process(im, Integer.valueOf(pwd), false);
            }
            return Contact.uploadImage(sender.getGroup(), MagicImage.bufferedImage2InputStream(de));
        } catch (Exception e) {
            return new PlainText("图片混沌处理:\n" +
                    "加密: 混沌 加密 [pwd] [原图]\n" +
                    "解密: 混沌 解密 [pwd] [原图]\n" +
                    "注意: 图片尺寸不宜过大,越大效率越低");
        }
    }
}
