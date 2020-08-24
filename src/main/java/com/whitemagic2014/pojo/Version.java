package com.whitemagic2014.pojo;

/**
 * @Description: 版本号
 * @author: magic chen
 * @date: 2020/8/20 15:23
 **/
public class Version implements Comparable<Version> {

    Long main;

    Long child;

    public Version() {
        super();
    }

    public Version(Long main, Long child) {
        this.main = main;
        this.child = child;
    }

    public Version(String ver) {
        String[] temp = ver.split("\\.");
        this.main = Long.valueOf(temp[0]);
        this.child = Long.valueOf(temp[1]) * 10L + Long.valueOf(temp[2]);
    }

    @Override
    public String toString() {
        Long m = this.child / 10;
        Long e = this.child % 10;
        return this.main + "." + m + "." + e;
    }

    public Long getMain() {
        return main;
    }

    public void setMain(Long main) {
        this.main = main;
    }

    public Long getChild() {
        return child;
    }

    public void setChild(Long child) {
        this.child = child;
    }

    public Version upMain() {
        this.main++;
        this.child = 0L;
        return this;
    }

    public Version upChild() {
        this.child++;
        return this;
    }


    @Override
    public int compareTo(Version o) {
        if (this.main > o.getMain()) {
            return 1;
        } else if (this.main < o.getMain()) {
            return -1;
        } else {
            if (this.child > o.getChild()) {
                return 1;
            } else if (this.child < o.getChild()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
