package com.whitemagic2014.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.WhiteMagic2014.Gmp;
import com.github.WhiteMagic2014.beans.DataEmbedding;
import com.github.WhiteMagic2014.function.GmpFunction;
import com.github.WhiteMagic2014.gptApi.Chat.pojo.ChatMessage;
import com.github.WhiteMagic2014.gptApi.GptModel;
import com.github.WhiteMagic2014.gptApi.Images.CreateImageRequest;
import com.github.WhiteMagic2014.gptApi.Images.pojo.OpenAiImage;
import com.github.WhiteMagic2014.util.VectorUtil;
import com.whitemagic2014.service.ChatPGTService;
import com.whitemagic2014.util.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: ChatPGTService
 * @author: magic chen
 * @date: 2023/2/10 15:28
 **/
@Service
public class ChatPGTServiceImpl implements ChatPGTService {

    @Autowired
    Gmp gmp;

    ExecutorService pool = Executors.newFixedThreadPool(10);

    @Value("${ChatGPT.model:gpt-4-1106-preview}")
    private String model;

    private String storage = "./storage/image";

    private List<DataEmbedding> vectors = new ArrayList<>(); // 预训练的数据集合

    @Override
    public OpenAiImage image(String prompt) {
        OpenAiImage image = new CreateImageRequest().prompt(prompt)
                .model(GptModel.Dall_E_3)
                .styleVivid()
                .size1024x1024().
                formatUrl().sendForImages().get(0);
        savePic(prompt, image.getRevised_prompt(), image.getUrl());
        return image;
    }

    private void savePic(String userPrompt, String prompt, String imageUrl) {
        pool.execute(() -> {
            String uuid = UUID.randomUUID().toString();
            // 下载 图片
            String imgPath = storage + File.separator + "draw-" + uuid + ".png";
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                    FileOutputStream out = new FileOutputStream(new File(imgPath));
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    // 下载完成，关闭输入输出流
                    in.close();
                    out.close();
                    connection.disconnect();
                } else {
                    System.out.println("无法下载图片，响应码：" + responseCode);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            // 存储 prompt
            String promptPath = storage + File.separator + "draw-" + uuid + ".txt";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(promptPath))) {
                bw.write("用户:\n" + userPrompt);
                bw.newLine();
                bw.write("dall-e-3:\n" + prompt);
                bw.newLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }


    @Override
    public String chat(String session, String prompt) {
        return gmp.chat(session, prompt, 1024);
    }

    @Override
    public String chat(String session, String prompt, List<GmpFunction> functions) {
        return gmp.chat(session, ChatMessage.userMessage(prompt), model, 1024, functions);
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
        return gmp.originChat(messages, 500);
    }

    @Override
    public List<List<Double>> input2Vector(List<String> inputs) {
        return VectorUtil.input2Vector(inputs);
    }

    @Override
    public String answer(String question) {
        if (vectors.isEmpty()) {
            return "无预训练数据";
        }
        // 由于vectors存储在内存中,所以深复制一层,避免并发的时候 question距离冲突，如果存在redis里面临时拿出来就不用深复制一次了
        return gmp.answer(question, new ArrayList<>(vectors), 3);
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
