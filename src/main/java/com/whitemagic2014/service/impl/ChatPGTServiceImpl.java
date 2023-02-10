package com.whitemagic2014.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whitemagic2014.service.ChatPGTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: ChatPGTService
 * @author: magic chen
 * @date: 2023/2/10 15:28
 **/
@Service
public class ChatPGTServiceImpl implements ChatPGTService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${ChatGPT.key}")
    String key;

    @Value("${ChatGPT.org}")
    String org;

    @Override
    public List<String> image(String prompt, int n) {
        JSONObject temp = createImage(prompt, n);
        if (temp == null) {
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
    public String chat(String prompt) {
        JSONObject temp = completions(prompt);
        if (temp == null) {
            return "出错了";
        }
        JSONObject result = temp.getJSONArray("choices").getJSONObject(0);
        return result.getString("text");
    }


    private JSONObject completions(String prompt) {
        String url = "https://api.openai.com/v1/completions";
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(key);
        header.setContentType(MediaType.APPLICATION_JSON);
        JSONObject json = new JSONObject();
        json.put("model", "text-davinci-003");
        json.put("prompt", prompt);
        json.put("temperature", 1);
        json.put("max_tokens", 2000);
        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), header);
        ResponseEntity<JSONObject> result;
        try {
            result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private JSONObject createImage(String prompt, int n) {
        String url = "https://api.openai.com/v1/images/generations";
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(key);
        header.setContentType(MediaType.APPLICATION_JSON);
        JSONObject json = new JSONObject();
        json.put("prompt", prompt);
        json.put("n", n);
        json.put("size", "1024x1024");
        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), header);
        ResponseEntity<JSONObject> result;
        try {
            result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
            return result.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
