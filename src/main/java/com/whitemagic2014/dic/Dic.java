package com.whitemagic2014.dic;

/**
 * @Description: 全局固定字段
 * @author: magic chen
 * @date: 2020/8/22 23:52
 **/
public interface Dic {

    Long[] BossHp = new Long[]{6000000L, 8000000L, 10000000L, 12000000L, 20000000L};

    // 在更新jjc文件的时候锁定 key
    String JJC_NICK_LOCK = "JJC_NICK_LOCK";

    // jjc 查询的缓存
    String JJC_CACHE = "JJC_CACHE";

    //pcr 工会战组件
    String Component_Pcr_Guild = "component.pcr.guild";

    // pcr jjc组件
    String Component_Pcr_JJC = "component.pcr.jjc";

    // pcr jjc图形组件
    String Component_Pcr_JJC_Image = "component.pcr.jjc.image";

    // ChatGPT
    String Component_ChatGPT = "component.ChatGPT";


}
