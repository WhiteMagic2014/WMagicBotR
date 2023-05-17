package com.whitemagic2014.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.WhiteMagic2014.gptApi.Chat.CreateChatCompletionRequest;
import com.github.WhiteMagic2014.gptApi.Embeddings.CreateEmbeddingsRequest;
import com.github.WhiteMagic2014.gptApi.Images.CreateImageRequest;
import com.github.WhiteMagic2014.util.Distance;
import com.whitemagic2014.pojo.DataEmbedding;
import com.whitemagic2014.service.ChatPGTService;
import com.whitemagic2014.util.Path;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

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

    private List<DataEmbedding> vectors = new ArrayList<>(); // 预训练的数据集合

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

    @Override
    public List<List<Double>> input2Vector(List<String> inputs) {
        CreateEmbeddingsRequest request = new CreateEmbeddingsRequest()
                .key(key);
        if (inputs.size() == 1) {
            request.input(inputs.get(0));
        } else {
            String[] ins = new String[inputs.size()];
            inputs.toArray(ins);
            request.inputs(ins);
        }
        return request.sendForEmbeddings();
    }


    @Override
    public String answer(String question) {
        if (vectors.isEmpty()) {
            return "无预训练数据";
        }
        List<Double> questionEmbedding = input2Vector(Collections.singletonList(question)).get(0);
        List<DataEmbedding> sorted = vectors.parallelStream()
                .map(de -> { // 由于vectors存储在内存中,所以深复制一层,避免并发的时候 question距离冲突，如果存在redis里面临时拿出来就不用深复制一次了
                    DataEmbedding deepClone = new DataEmbedding();
                    BeanUtils.copyProperties(de, deepClone);
                    return deepClone;
                })
                .peek(de -> de.setEmbeddingWithQuery(Distance.cosineDistance(questionEmbedding, de.getContextEmbedding())))
                .sorted(Comparator.comparing(DataEmbedding::getEmbeddingWithQuery).reversed())
                .collect(Collectors.toList());
        List<OriginChatVO> templates = new ArrayList<>();
        templates.add(new OriginChatVO("system", "根据下面的参考回答问题，请直接回答问题，如果无法回答问题，回答“我不知道”。"));
        StringBuilder prompt = new StringBuilder("参考:\n");
        for (int i = 0; i < Math.min(3, sorted.size()); i++) {
            prompt.append(sorted.get(i).getContext());
        }
        prompt.append("\n问题:\n").append(question);
        templates.add(new OriginChatVO("user", prompt.toString()));
        return originChat(templates);
    }


    @Override
    public String reloadVector() {
        List<DataEmbedding> tem = new ArrayList<>();
        File nameFile = new File(Path.getPath() + "embeddings.json");
        if (nameFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(nameFile))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    tem.add(JSON.parseObject(line, DataEmbedding.class));
                }
                vectors = tem;
                return "成功读取 " + vectors.size() + " 条向量";
            } catch (Exception e) {
                return "读取embeddings.json 失败";
            }
        } else {
            return "embeddings.json 不存在";
        }
    }

}
