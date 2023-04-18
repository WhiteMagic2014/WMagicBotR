package com.whitemagic2014.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.WhiteMagic2014.gptApi.Chat.CreateChatCompletionRequest;
import com.github.WhiteMagic2014.gptApi.Images.CreateImageRequest;
import com.whitemagic2014.service.ChatPGTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: ChatPGTService
 * @author: magic chen
 * @date: 2023/2/10 15:28
 **/
@Service("ChatPGTServiceImpl")
public class ChatPGTServiceImpl implements ChatPGTService {


    @Value("${ChatGPT.key}")
    private String key;

    @Value("${ChatGPT.org}")
    private String org;


    private Map<String, Queue<ChatLog>> logs = new HashMap<>(); // 对话上下文
    private Map<String, String> personality = new HashMap<>(); //性格设定

    private int maxLog = 5; // 最大记忆层数

    @Override
    public List<String> image(String prompt, int n) {
        JSONObject temp = null;
        try {
            temp = new CreateImageRequest()
                    .key(key)
                    .prompt(prompt)
                    .n(n)
                    .largeSize()
                    .send();
        } catch (Exception e) {
            return Collections.singletonList("出错了");
        }
        List<String> resultList = new ArrayList<>();
        JSONArray array = temp.getJSONArray("data");
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            resultList.add(jsonObject.getString("url"));
        }
        return resultList;
    }

    @Override
    public String chat(String session, String prompt) {
        String personal = personality.getOrDefault(session, "与用户进行闲聊或娱乐性的对话，以改善用户体验。");
        // 构造初始请求
        CreateChatCompletionRequest request = new CreateChatCompletionRequest()
                .key(key)
                .maxTokens(500)
                .addMessage("system", personal);
        // 拼接历史对话记录
        if (logs.containsKey(session)) {
            Queue<ChatLog> queue = logs.get(session);
            queue.forEach(l -> {
                request.addMessage("user", l.getUser());
                System.out.println(l.getUser());
                request.addMessage("assistant", l.getAssistant());
                System.out.println(l.getAssistant());
            });
        }
        request.addMessage("user", prompt);
        // 发送请求
        String result = "";
        try {
            result = request.sendForChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            try {
                JSONObject js = JSONObject.parseObject(e.getMessage());
                // 如果是长度超了。 遗忘一段记忆
                if (js.getJSONObject("error").getString("code").equals("context_length_exceeded")) {
                    if (logs.containsKey(session)) {
                        Queue<ChatLog> queue = logs.get(session);
                        queue.poll();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return "很抱歉，出错了";
        }
        // 记忆上下文
        if (logs.containsKey(session)) {
            Queue<ChatLog> queue = logs.get(session);
            if (queue.size() > maxLog) {
                queue.poll();
            }
            queue.offer(new ChatLog(prompt, result));
        } else {
            Queue<ChatLog> queue = new LinkedList<>();
            queue.offer(new ChatLog(prompt, result));
            logs.put(session, queue);
        }
        return result;
    }

    @Override
    public String setPersonality(String session, String setting) {
        personality.put(session, setting);
        return "已经设定为: " + setting;
    }

    @Override
    public String clearLog(String session) {
        logs.remove(session);
        return "操作成功";
    }

    @Override
    public String originChat(List<OriginChatVO> voList) {
        CreateChatCompletionRequest request = new CreateChatCompletionRequest()
                .key(key)
                .maxTokens(500);
        for (OriginChatVO vo : voList) {
            request.addMessage(vo.getRole(), vo.getPrompt());
        }
        String result = "";
        try {
            result = request.sendForChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            return "很抱歉，出错了";
        }
        return result;
    }

}
