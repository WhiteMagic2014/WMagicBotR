package com.whitemagic2014.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.WhiteMagic2014.Gmp;
import com.github.WhiteMagic2014.beans.DataEmbedding;
import com.github.WhiteMagic2014.gptApi.Chat.pojo.ChatMessage;
import com.whitemagic2014.service.ChatPGTService;
import com.whitemagic2014.util.Path;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: ChatPGTService
 * @author: magic chen
 * @date: 2023/2/10 15:28
 **/
@Service
public class ChatPGTServiceImpl implements ChatPGTService {

    @Autowired
    Gmp gmp;

    @Value("${ChatGPT.chat.stream}")
    private boolean streamModel;

    private List<DataEmbedding> vectors = new ArrayList<>(); // 预训练的数据集合

    @Override
    public List<String> image(String prompt, int n) {
        return gmp.image(prompt, n);
    }

    @Override
    public String chat(String session, String prompt) {
        return gmp.chat(session, prompt, 500, streamModel);
    }

    @Override
    public String setPersonality(String session, String setting) {
        gmp.setPersonality(session, setting);
        return "已经设定为: " + setting;
    }

    @Override
    public String clearLog(String session) {
        gmp.clearLog(session);
        return "操作成功";
    }

    @Override
    public String originChat(List<ChatMessage> messages) {
        return gmp.originChat(messages, 500, streamModel);
    }

    @Override
    public List<List<Double>> input2Vector(List<String> inputs) {
        return gmp.input2Vector(inputs);
    }

    @Override
    public String answer(String question) {
        if (vectors.isEmpty()) {
            return "无预训练数据";
        }
        // 由于vectors存储在内存中,所以深复制一层,避免并发的时候 question距离冲突，如果存在redis里面临时拿出来就不用深复制一次了
        List<DataEmbedding> embeddings = vectors.parallelStream().map(de -> {
            DataEmbedding deepClone = new DataEmbedding();
            BeanUtils.copyProperties(de, deepClone);
            return deepClone;
        }).collect(Collectors.toList());
        return gmp.answer(question, embeddings);
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
