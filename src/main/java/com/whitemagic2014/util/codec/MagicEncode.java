package com.whitemagic2014.util.codec;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 自用编码
 * @author: magic chen
 * @date: 2020/8/21 18:10
 **/
public class MagicEncode {

    static String spare = "分";

    static HashMap<String, String> dicDef = new HashMap<String, String>() {
        {
            put("0", "夏");
            put("1", "商");
            put("2", "周");
            put("3", "秦");
            put("4", "西");
            put("5", "新");
            put("6", "东");
            put("7", "三");
            put("8", "晋");
            put("9", "朝");
            put("A", "隋");
            put("B", "唐");
            put("C", "宋");
            put("D", "元");
            put("E", "明");
            put("F", "清");
        }
    };

    public static String encode(String str, CustomHex hex) {
        return replaceWithDic(str2HexStr(str), hex).replace(" ", "");
    }

    public static String decode(String str, CustomHex hex) {
        return hexStr2Str(replaceWithDic(str, hex));
    }

    public static String encodeWithPunc(String str, CustomHex hex) {
        char[] chars = str.toCharArray();
        String result = "";
        for (char c : chars) {
            if (isPunctuation(c)) {
                result += spare + c;
            } else {
                result += spare + str2HexStr(String.valueOf(c)).replace(" ", "");
            }
        }
        return replaceWithDic(result, hex);
    }

    public static String decodeWithPunc(String str, CustomHex hex) {
        char[] chars = replaceWithDic(str, hex).toCharArray();
        String result = "";
        String temp = "";
        for (char c : chars) {
            if (isPunctuation(c)) {
                result += c;
            } else {
                if (String.valueOf(c).equals(spare)) {
                    result += hexStr2Str(temp);
                    temp = "";
                } else {
                    temp += c;
                }
            }
        }
        if (!temp.equals("")) {
            result += hexStr2Str(temp);
        }
        return result;
    }

    static String replaceWithDic(String str, CustomHex hex) {
        HashMap<String, String> dic = dicDef;
        if (hex != null) {
            dic = hex.toMap();
        }

        Map<String, String> dic2 = new HashMap<>();
        // 键值反转做补完dic
        for (String key : dic.keySet()) {
            dic2.put(dic.get(key), key);
        }
        dic2.putAll(dic);
        char[] chars = str.toCharArray();
        String result = "";
        for (char c : chars) {
            String s = String.valueOf(c);
            result += dic2.getOrDefault(s, s);
        }
        return result;
    }

    static String hexStr2Str(String hex) {
        String hexStr = "";
        String str = "0123456789ABCDEF"; // 16进制能用到的所有字符 0-15
        for (int i = 0; i < hex.length(); i++) {
            String s = hex.substring(i, i + 1);
            if (s.equals("a") || s.equals("b") || s.equals("c") || s.equals("d") || s.equals("e") || s.equals("f")) {
                s = s.toUpperCase().substring(0, 1);
            }
            hexStr += s;
        }

        char[] hexs = hexStr.toCharArray();// toCharArray() 方法将字符串转换为字符数组。
        int length = (hexStr.length() / 2);// 1个byte数值 -> 两个16进制字符
        byte[] bytes = new byte[length];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            int position = i * 2;// 两个16进制字符 -> 1个byte数值
            n = str.indexOf(hexs[position]) * 16;
            n += str.indexOf(hexs[position + 1]);
            // 保持二进制补码的一致性 因为byte类型字符是8bit的 而int为32bit 会自动补齐高位1 所以与上0xFF之后可以保持高位一致性
            // 当byte要转化为int的时候，高的24位必然会补1，这样，其二进制补码其实已经不一致了，&0xff可以将高的24位置为0，低8位保持原样，这样做的目的就是为了保证二进制数据的一致性。
            bytes[i] = (byte) (n & 0xff);
        }
        String name = "";
        try {
            name = new String(bytes, "gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return name;
    }

    static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();// toCharArray() 方法将字符串转换为字符数组。
        StringBuilder sb = new StringBuilder(""); // StringBuilder是一个类，可以用来处理字符串,sb.append()字符串相加效率高
        byte[] bs = null;
        try {
            bs = str.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// String的getBytes()方法是得到一个操作系统默认的编码格式的字节数组
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4; // 高4位, 与操作 1111 0000
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f; // 低四位, 与操作 0000 1111
            sb.append(chars[bit]);
            sb.append(' ');// 每个Byte之间空格分隔
        }
        return sb.toString().trim();
    }

    static boolean isPunctuation(char ch) {
        if (isCjkPunc(ch))
            return true;
        if (isEnPunc(ch))
            return true;

        if (0x2018 <= ch && ch <= 0x201F)
            return true;
        if (ch == 0xFF01 || ch == 0xFF02)
            return true;
        if (ch == 0xFF07 || ch == 0xFF0C)
            return true;
        if (ch == 0xFF1A || ch == 0xFF1B)
            return true;
        if (ch == 0xFF1F || ch == 0xFF61)
            return true;
        if (ch == 0xFF0E)
            return true;
        if (ch == 0xFF65)
            return true;

        return false;
    }

    static boolean isEnPunc(char ch) {
        if (0x21 <= ch && ch <= 0x22)
            return true;
        if (ch == 0x27 || ch == 0x2C)
            return true;
        if (ch == 0x2E || ch == 0x3A)
            return true;
        if (ch == 0x3B || ch == 0x3F)
            return true;

        return false;
    }

    static boolean isCjkPunc(char ch) {
        if (0x3001 <= ch && ch <= 0x3003)
            return true;
        if (0x301D <= ch && ch <= 0x301F)
            return true;

        return false;
    }

}
