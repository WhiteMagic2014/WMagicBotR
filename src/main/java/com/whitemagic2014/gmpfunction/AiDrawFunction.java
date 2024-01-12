package com.whitemagic2014.gmpfunction;

import com.alibaba.fastjson.JSONObject;
import com.github.WhiteMagic2014.function.GmpFunction;
import com.github.WhiteMagic2014.function.HandleResult;
import com.github.WhiteMagic2014.gptApi.Images.pojo.OpenAiImage;
import com.github.WhiteMagic2014.tool.FunctionTool;
import com.github.WhiteMagic2014.tool.GptFunction;
import com.whitemagic2014.annotate.Function;
import com.whitemagic2014.service.ChatPGTService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Function
public class AiDrawFunction extends GmpFunction {


    @Autowired
    ChatPGTService service;

    private static final Random random = new Random();

    private List<String> templatePool = Arrays.asList(
            "我已经为您完成了作品 \n{url}",
            "您好，我已经按照您的要求完成了作品，请点击下方链接查看：\n{url}",
            "您的作品已经制作完成了，请点击下方链接查看：\n{url}",
            "您好，作品已经制作完成，请点击下方链接查看：\n{url}"
    );

    /**
     * 随机获得一个返回模版
     *
     * @param url
     * @return
     */
    private String getTemplate(String url) {
        return templatePool.get(random.nextInt(templatePool.size())).replace("{url}", url);
    }


    @Override
    public String getName() {
        return "drawPic";
    }

    @Override
    public FunctionTool getFunctionTool() {
        GptFunction function = new GptFunction();
        function.setName(getName());
        function.setDescription("根据用户的描述生成图片");
        JSONObject fp = new JSONObject();
        fp.put("type", "object");
        fp.put("required", Collections.singletonList("prompt"));
        JSONObject p = new JSONObject();
        JSONObject iprompt = new JSONObject();
        iprompt.put("type", "string");
        iprompt.put("description", "用来生成图片的描述");
        p.put("prompt", iprompt);
        fp.put("properties", p);
        function.setParameters(fp);
        return FunctionTool.functionTool(function);
    }

    @Override
    public HandleResult handle(JSONObject arguments) {
        String prompt = arguments.getString("prompt");
        String result;
        try {
            OpenAiImage image = service.image(prompt);
//            result = "返回格式为:[图片名](图片地址) 注意,请将完整的地址返回，不要截取url中的任何部分 图片地址:\n" + image.getUrl();
            result = getTemplate(image.getUrl()) + "\n请注意 此链接有时效性,请尽快保存作品";
        } catch (Exception e) {
            result = "很抱歉,作图的时候出了一点问题,可以稍后再次尝试";
        }
        return new HandleResult(false, result);
    }
}
