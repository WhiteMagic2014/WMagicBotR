package com.whitemagic2014.pojo;

import com.whitemagic2014.util.DateFormatUtil;

import java.util.Date;

/**
 * @Description: db数据结构版本
 * @author: magic chen
 * @date: 2020/8/20 15:22
 **/
public class BotDB {
    Integer id;

    String name;

    String version;

    String createDate;

    String updateDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.updateDate = DateFormatUtil.sdf.format(new Date());
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.updateDate = DateFormatUtil.sdf.format(new Date());
        this.version = version;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public BotDB upMainVer() {
        Version ver = new Version(version);
        this.version = ver.upMain().toString();
        this.updateDate = DateFormatUtil.sdf.format(new Date());
        return this;
    }

    public BotDB upChildVer() {
        Version ver = new Version(version);
        this.version = ver.upChild().toString();
        this.updateDate = DateFormatUtil.sdf.format(new Date());
        return this;
    }

    @Override
    public String toString() {
        return "BotDB [id=" + id + ", name=" + name + ", version=" + version + ", createDate=" + createDate
                + ", updateDate=" + updateDate + "]";
    }
}
