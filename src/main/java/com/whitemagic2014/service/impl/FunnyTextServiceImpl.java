package com.whitemagic2014.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.whitemagic2014.dao.FunnyTextDao;
import com.whitemagic2014.pojo.FunnyText;
import com.whitemagic2014.service.FunnyTextService;
import com.whitemagic2014.util.MagicMd5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FunnyTextServiceImpl implements FunnyTextService {

    @Autowired
    RestTemplate template;

    @Autowired
    FunnyTextDao funnyTextDao;

    private Map<String, String> chpCache = null;
    private Map<String, String> djtCache = null;
    private Map<String, String> pyqCache = null;


    // 登记开启彩虹屁的用户
    private Set<String> chpUser = new HashSet<>();

    @Override
    public String getChp() {
        try {
            JSONObject result = template.getForObject("https://api.shadiao.app/chp", JSONObject.class);
            String text = result.getJSONObject("data").getString("text");
            String md5 = MagicMd5.getMd5String(text);
            if (funnyTextDao.getChpByHash(md5) == null) {
                funnyTextDao.insertChp(new FunnyText(md5, text));
                if (chpCache == null) {
                    chpCache = funnyTextDao.loadAllChp().stream().collect(Collectors.toMap(FunnyText::getHash, FunnyText::getContent));
                } else {
                    chpCache.put(md5, text);
                }
            }
            return text;
        } catch (Exception e) {
            return getChpByCache();
        }
    }


    @Override
    public String getDjt() {
        try {
            JSONObject result = template.getForObject("https://api.shadiao.app/du", JSONObject.class);
            String text = result.getJSONObject("data").getString("text");
            String md5 = MagicMd5.getMd5String(text);
            if (funnyTextDao.getDjtByHash(md5) == null) {
                funnyTextDao.insertDjt(new FunnyText(md5, text));
                if (djtCache == null) {
                    djtCache = funnyTextDao.loadAllDjt().stream().collect(Collectors.toMap(FunnyText::getHash, FunnyText::getContent));
                } else {
                    djtCache.put(md5, text);
                }
            }
            return text;
        } catch (Exception e) {
            return getDjtByCache();
        }
    }

    @Override
    public String getPyq() {
        try {
            JSONObject result = template.getForObject("https://api.shadiao.app/pyq", JSONObject.class);
            String text = result.getJSONObject("data").getString("text");
            String md5 = MagicMd5.getMd5String(text);
            if (funnyTextDao.getPyqByHash(md5) == null) {
                funnyTextDao.insertPyq(new FunnyText(md5, text));
                if (pyqCache == null) {
                    pyqCache = funnyTextDao.loadAllPyq().stream().collect(Collectors.toMap(FunnyText::getHash, FunnyText::getContent));
                } else {
                    pyqCache.put(md5, text);
                }
            }
            return text;
        } catch (Exception e) {
            return getPyqByCache();
        }
    }

    @Override
    public void registChp(Long gid, Long uid) {
        String chpKey = gid + "_" + uid;
        chpUser.add(chpKey);
    }

    @Override
    public void unRegistChp(Long gid, Long uid) {
        String chpKey = gid + "_" + uid;
        chpUser.remove(chpKey);
    }

    @Override
    public boolean checkRequireChp(Long gid, Long uid) {
        String chpKey = gid + "_" + uid;
        return chpUser.contains(chpKey);
    }

    private String getChpByCache() {
        List<String> list = new ArrayList<>(chpCache.values());
        Collections.shuffle(list);
        return list.get(0);
    }

    private String getDjtByCache() {
        List<String> list = new ArrayList<>(djtCache.values());
        Collections.shuffle(list);
        return list.get(0);
    }

    private String getPyqByCache() {
        List<String> list = new ArrayList<>(pyqCache.values());
        Collections.shuffle(list);
        return list.get(0);
    }

}
