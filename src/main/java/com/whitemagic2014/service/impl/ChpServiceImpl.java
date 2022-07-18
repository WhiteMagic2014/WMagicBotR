package com.whitemagic2014.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.whitemagic2014.dao.ChpDao;
import com.whitemagic2014.pojo.Chp;
import com.whitemagic2014.service.ChpService;
import com.whitemagic2014.util.MagicMd5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChpServiceImpl implements ChpService {

    @Autowired
    RestTemplate template;

    @Autowired
    ChpDao chpDao;

    private Map<String, String> chpCache = null;

    @Override
    public String getChp() {

        try {
            JSONObject result = template.getForObject("https://api.shadiao.app/chp", JSONObject.class);
            String chp = result.getJSONObject("data").getString("text");
            String md5 = MagicMd5.getMd5String(chp);
            if (chpDao.getByHash(md5) == null) {
                chpDao.insert(new Chp(md5, chp));
                if (chpCache == null) {
                    chpCache = chpDao.loadAll().stream().collect(Collectors.toMap(Chp::getHash, Chp::getContent));
                } else {
                    chpCache.put(md5, chp);
                }
            }
            return chp;
        } catch (Exception e) {
            return getByCache();
        }
    }


    private String getByCache() {
        List<String> chpList = new ArrayList<>(chpCache.values());
        Collections.shuffle(chpList);
        return chpList.get(0);
    }

}
