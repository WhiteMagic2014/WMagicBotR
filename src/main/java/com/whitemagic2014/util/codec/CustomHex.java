package com.whitemagic2014.util.codec;

import java.util.HashMap;

/**
 * @Description: 自定义hex编码接收vo
 * @author: magic chen
 * @date: 2020/8/21 18:11
 **/
public class CustomHex {
    String hex0;
    String hex1;
    String hex2;
    String hex3;
    String hex4;
    String hex5;
    String hex6;
    String hex7;
    String hex8;
    String hex9;
    String hexA;
    String hexB;
    String hexC;
    String hexD;
    String hexE;
    String hexF;

    public String getHex0() {
        return hex0;
    }

    public void setHex0(String hex0) {
        this.hex0 = hex0;
    }

    public String getHex1() {
        return hex1;
    }

    public void setHex1(String hex1) {
        this.hex1 = hex1;
    }

    public String getHex2() {
        return hex2;
    }

    public void setHex2(String hex2) {
        this.hex2 = hex2;
    }

    public String getHex3() {
        return hex3;
    }

    public void setHex3(String hex3) {
        this.hex3 = hex3;
    }

    public String getHex4() {
        return hex4;
    }

    public void setHex4(String hex4) {
        this.hex4 = hex4;
    }

    public String getHex5() {
        return hex5;
    }

    public void setHex5(String hex5) {
        this.hex5 = hex5;
    }

    public String getHex6() {
        return hex6;
    }

    public void setHex6(String hex6) {
        this.hex6 = hex6;
    }

    public String getHex7() {
        return hex7;
    }

    public void setHex7(String hex7) {
        this.hex7 = hex7;
    }

    public String getHex8() {
        return hex8;
    }

    public void setHex8(String hex8) {
        this.hex8 = hex8;
    }

    public String getHex9() {
        return hex9;
    }

    public void setHex9(String hex9) {
        this.hex9 = hex9;
    }

    public String getHexA() {
        return hexA;
    }

    public void setHexA(String hexA) {
        this.hexA = hexA;
    }

    public String getHexB() {
        return hexB;
    }

    public void setHexB(String hexB) {
        this.hexB = hexB;
    }

    public String getHexC() {
        return hexC;
    }

    public void setHexC(String hexC) {
        this.hexC = hexC;
    }

    public String getHexD() {
        return hexD;
    }

    public void setHexD(String hexD) {
        this.hexD = hexD;
    }

    public String getHexE() {
        return hexE;
    }

    public void setHexE(String hexE) {
        this.hexE = hexE;
    }

    public String getHexF() {
        return hexF;
    }

    public void setHexF(String hexF) {
        this.hexF = hexF;
    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> dic = new HashMap<String, String>();
        dic.put("0", getHex0());
        dic.put("1", getHex1());
        dic.put("2", getHex2());
        dic.put("3", getHex3());
        dic.put("4", getHex4());
        dic.put("5", getHex5());
        dic.put("6", getHex6());
        dic.put("7", getHex7());
        dic.put("8", getHex8());
        dic.put("9", getHex9());
        dic.put("A", getHexA());
        dic.put("B", getHexB());
        dic.put("C", getHexC());
        dic.put("D", getHexD());
        dic.put("E", getHexE());
        dic.put("F", getHexF());
        return dic;
    }

}
