package com.whitemagic2014.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whitemagic2014.dic.Dic;
import com.whitemagic2014.dic.ReturnCode;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcrjjc.Answer;
import com.whitemagic2014.service.Pcrjjc;
import com.whitemagic2014.util.MagicMaps;
import com.whitemagic2014.util.Path;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description: jjc 实现类
 * @author: magic chen
 * @date: 2020/8/25 17:43
 **/
@Service
public class PcrjjcImpl implements Pcrjjc {


    private static final Logger logger = LoggerFactory.getLogger(PcrjjcImpl.class);

    @Value("${pcrdfans.key}")
    String pcrdfansKey;

    @Value("${pcrdfans.url}")
    String pcrdfansUrl;

    @Value("${pcrdfans.open}")
    Boolean pcrdfansOpen;

    @Value("${pcr.default.nicknamefile}")
    String defaultNicknameFileUrl;

    @Value("${pcr.default.nickname}")
    String defaultNicknameUrl;

    @Autowired
    RestTemplate template;


    Map<String, Integer> nameId = new HashMap<>();

    Map<Integer, List<String>> idNames = new HashMap<>();


    @Override
    public void initNameFile() {
        if (pcrdfansOpen) {
            File nameFile = new File(Path.getPath() + "nicknames.txt");
            if (nameFile.exists()) {
                logger.info("nicknames.txt 文件存在");
                // 制造map
                makeMap();
            } else {
                logger.info("nicknames.txt 文件不存在,尝试获取最新版本");
                refreshNameFile(null);
            }
        } else {
            logger.info("未开启jjc查询功能");
        }
    }

    @Override
    public PrivateModel<String> refreshNameFile(String url) {
        try {
            //开始刷新的时候加一个弱检查锁
            MagicMaps.put(Dic.JJC_NICK_LOCK, "val");

            if (StringUtils.isBlank(url)) {
                url = defaultNicknameFileUrl;
            }
            logger.info("开始下载文件,文件地址:" + url);
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);

            ResponseEntity<byte[]> response;
            try {
                response = template.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
            } catch (Exception e) {
                logger.info("下载失败!", e);
                return new PrivateModel<>(ReturnCode.FAIL, "下载失败:" + e.getMessage());
            }

            File file = new File(Path.getPath() + "nicknames.txt");
            try (FileOutputStream fos = new FileOutputStream(file);) {
                fos.write(response.getBody());
            } catch (IOException e) {
                logger.info("下载失败!", e);
                return new PrivateModel<>(ReturnCode.FAIL, "下载失败:" + e.getMessage());
            }
            logger.info("下载成功!");
            // 制造map
            makeMap();
            return new PrivateModel<>(ReturnCode.SUCCESS, "success", "更新成功");
        } finally {
            //不管何种原因结束 解锁
            MagicMaps.remove(Dic.JJC_NICK_LOCK);
        }
    }


    // region  国服 2; 台服 3; 日服 4
    @Override
    public PrivateModel<List<Answer>> checkjjc(List<String> names, Integer region) {
        if (!pcrdfansOpen) return new PrivateModel<>(ReturnCode.FAIL, "未启用jjc查询功能,请联系管理员修改配置后重启");
        if (StringUtils.isBlank(pcrdfansKey)) return new PrivateModel<>(ReturnCode.FAIL, "jjc查询不可用,未配置pcrdfans key");
        // 查询前需要check一下当前是否在更新 更新中不可用
        if (MagicMaps.check(Dic.JJC_NICK_LOCK)) return new PrivateModel<>(ReturnCode.FAIL, "正在获取最新nickname表,请稍后查询");
        if (names.isEmpty() || names.size() > 5) return new PrivateModel<>(ReturnCode.FAIL, "请输入5位选手名,空格分开");

        PrivateModel<List<Integer>> idModel = nameToId(names);
        if (!idModel.isSuccess()) {
            return new PrivateModel<List<Answer>>().wrapper(idModel);
        }
        List<Integer> ids = idModel.getReturnObject();
        // 去读缓存 有缓存就不请求了
        String memKey = Dic.JJC_CACHE + ids.stream().sorted().map(String::valueOf).reduce("", String::concat);
        if (MagicMaps.check(memKey)) {
            return new PrivateModel<>(ReturnCode.SUCCESS, "success", (List<Answer>) MagicMaps.getObject(memKey));
        }

        // 调用pcrdfans
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.set("authorization", pcrdfansKey);
        headers.set("user-agent", "WMagicBotR");
        String uuid = UUID.randomUUID().toString();
        JSONObject json = new JSONObject();
        json.put("_sign", "WMagicBotR" + uuid);
        json.put("def", ids);
        json.put("nonce", uuid);
        json.put("page", 1);
        json.put("sort", 1);
        json.put("ts", System.currentTimeMillis() / 1000);
        json.put("region", region);

        HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), headers);

        ResponseEntity<JSONObject> response;
        try {
            response = template.postForEntity(pcrdfansUrl, formEntity, JSONObject.class);
        } catch (Exception e) {
            return new PrivateModel<>(ReturnCode.FAIL, "查询失败。http请求错误:" + e.getMessage());
        }
        if (response.getStatusCodeValue() != 200) {
            return new PrivateModel<>(ReturnCode.FAIL, "查询失败。请求错误,状态码:" + response.getStatusCodeValue());
        }
        JSONObject result = response.getBody();

        // 测试数据
//        JSONObject  result = JSONObject.parseObject("{\"code\":0,\"message\":\"0\",\"data\":{\"result\":[{\"id\":\"5ef44ae98d2019c3adf709a2\",\"atk\":[{\"equip\":false,\"id\":103801,\"star\":4},{\"equip\":false,\"id\":105301,\"star\":3},{\"equip\":false,\"id\":104301,\"star\":3},{\"equip\":false,\"id\":101701,\"star\":4},{\"equip\":false,\"id\":100701,\"star\":4}],\"def\":[{\"equip\":false,\"id\":105901,\"star\":4},{\"equip\":false,\"id\":104601,\"star\":4},{\"equip\":false,\"id\":102901,\"star\":5},{\"equip\":false,\"id\":104701,\"star\":4},{\"equip\":false,\"id\":104501,\"star\":5}],\"iseditor\":false,\"private\":false,\"group\":false,\"updated\":\"2020-07-05T02:20:39.346Z\",\"up\":5,\"down\":0,\"comment\":null,\"liked\":false,\"disliked\":false}],\"page\":{\"page\":1,\"hasMore\":false}},\"version\":\"20200818\"}");
        Integer code = result.getIntValue("code");
        PrivateModel<String> checkCode = checkPcrdfansCode(code);
        if (checkCode.isSuccess()) {
            JSONObject data = result.getJSONObject("data");
            List<Answer> answers = JSON.parseArray(data.getJSONArray("result").toJSONString(), Answer.class);
            for (Answer answer : answers) {
                answer.getAtk().forEach(m -> {
                    m.setName(id2Name(m.getId()));
                });
                answer.getDef().forEach(m -> {
                    m.setName(id2Name(m.getId()));
                });
            }
            // 减少请求数量 这边做一个缓存 5分钟吧
            MagicMaps.putWithExpire(memKey, answers, 5L, TimeUnit.MINUTES);
            return new PrivateModel<>(ReturnCode.SUCCESS, "success", answers);
        } else {
            return new PrivateModel<>(ReturnCode.FAIL, "pcrdfans 错误码:" + code + "," + checkCode.getReturnMessage());
        }
    }


    @Override
    public String id2Name(Integer id) {
        if (!idNames.containsKey(id)) return "unkonw" + id;
        List<String> names = idNames.get(id);

        // 随机返回该角色的一个昵称
//        int index = MagicHelper.randomInt(names.size());
//        // 如果为0 那就给他改成1 ,因为0是日文名
//        if (index == 0) index = 1;
//        return names.get(index);

        // 默认返回官方译名
        return names.get(1);
    }

    /**
     * @Name: makeMap
     * @Description: 根据nickname file 创建映射map
     * @Param:
     * @Author: magic chen
     * @Date: 2020/8/26 16:56
     **/
    private void makeMap() {
        logger.info("开始构造map");
        File nameFile = new File(Path.getPath() + "nicknames.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(nameFile))) {
            Map<String, Integer> nameIdTemp = new HashMap<>();
            Map<Integer, List<String>> idNamesTemp = new HashMap<>();
            int start = 1;              // 从第几行开始读
            int end = Integer.MAX_VALUE; // 读到第几行
            //int end = 500;
            int i = start - 1;
            while (start-- > 1) {
                br.readLine();
            }
            String line = "";
            while ((line = br.readLine()) != null && i++ < end) {
                if (line.startsWith("id")) continue;
                String[] temp = line.split(",");
                Integer id = Integer.valueOf(temp[0]) * 100 + 1;
                List<String> names = new ArrayList<>();
                for (int j = 1; j < temp.length; j++) {
                    nameIdTemp.put(temp[j], id);
                    names.add(temp[j]);
                }
                idNamesTemp.put(id, names);
            }
            nameId.clear();
            nameId.putAll(nameIdTemp);
            idNames.clear();
            idNames.putAll(idNamesTemp);
        } catch (Exception e) {
            logger.error("构造name map出错", e);
        }
    }


    /**
     * @Name: nameToId
     * @Description: name 转 id
     * @Param: names
     * @Return:
     * @Author: magic chen
     * @Date: 2020/8/26 17:15
     **/
    private PrivateModel<List<Integer>> nameToId(List<String> names) {
        List<Integer> ids = new ArrayList<>();
        for (String name : names) {
            if (!nameId.containsKey(name)) {
                return new PrivateModel<>(ReturnCode.FAIL, "未知的昵称:" + name
                        + "。当前昵称表 请查看:" + defaultNicknameUrl);
            } else {
                ids.add(nameId.get(name));
            }
        }
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(ids);
        ids = ids.stream().distinct().collect(Collectors.toList());
        if (ids.size() < 5) return new PrivateModel<>(ReturnCode.FAIL, "请输入五个不同的角色");

        return new PrivateModel<>(ReturnCode.SUCCESS, "success", ids);
    }


    /**
     * @Name: checkPcrdfansCode
     * @Description: 解析pcrdfans api code
     * @Param: code
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/27 15:30
     **/
    private PrivateModel<String> checkPcrdfansCode(Integer code) {
        String msg = "";
        switch (code) {
            case 0:
                return new PrivateModel<>(ReturnCode.SUCCESS, "success");
            case 1:
                msg = "User is not login";
                break;
            case 2:
                msg = "Success but find nothing";
                break;
            case 3:
                msg = "Duplicated Battle";
                break;
            case 4:
                msg = "Duplicated Battle but updated info";
                break;
            case 5:
                msg = "Not enough elements";
                break;

            case 100:
                msg = "System Error";
                break;
            case 101:
                msg = "Database find data error";
                break;
            case 102:
                msg = "Unknow Parameters";
                break;
            case 103:
                msg = "Signature not valid";
                break;
            case 104:
                msg = "CORS not valid";
                break;
            case 105:
                msg = "Server Error";
                break;
            case 106:
                msg = "User exist";
                break;
            case 107:
                msg = "User not exist";
                break;
            case 108:
                msg = "Password not match";
                break;
            case 109:
                msg = "Duplicated data";
                break;
            case 110:
                msg = "Game unit not found";
                break;
            case 111:
                msg = "No Permission";
                break;
            case 112:
                msg = "Third party request error";
                break;
            case 113:
                msg = "Data is processing";
                break;
            case 114:
                msg = "Skill not found";
                break;
            case 115:
                msg = "Expired request";
                break;
            case 116:
                msg = "Contains sensitive word";
                break;
            case 117:
                msg = "Over limit";
                break;
            case 118:
                msg = "Pending request";
                break;
            case 129:
                msg = "Router not found";
                break;
            case 600:
                msg = "Unsupported region";
                break;
            case 601:
                msg = "IP blocked";
                break;
            case -429:
                msg = "Too many requests";
                break;

        }
        logger.error("pcrdfans返回" + code + ":" + msg);
        return new PrivateModel<>(ReturnCode.FAIL, msg);
    }


}
