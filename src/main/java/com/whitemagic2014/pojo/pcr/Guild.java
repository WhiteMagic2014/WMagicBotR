package com.whitemagic2014.pojo.pcr;

/**
 * @Description: pcr 工会模型
 * @author: magic chen
 * @date: 2020/8/22 11:37
 **/
public class Guild {

    // 群号
    Long gid;

    // 公会名（以创建时群名为准）
    String gname;


    public void setGid(Long gid) {
        this.gid = gid;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public Long getGid() {
        return gid;
    }

    public String getGname() {
        return gname;
    }
}
