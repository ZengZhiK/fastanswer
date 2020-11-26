package com.zzk.fastanswer.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串转Map工具类
 * @author zzk
 * @create 2020-10-17 16:02
 */
public class StringToMapUtils {
    /**
     * 字符串解析为Map
     * @param s 字符串，例如 k1=v1&k2=v2
     * @param separator 分隔符，例如 &
     * @return k、v对应的Map
     */
    public static Map<String, String> parse(String s, String separator){
        HashMap<String, String> result = new HashMap<>();
        String[] split = s.split(separator);
        for (String entry : split) {
            String[] keyValStr = entry.split("=");
            result.put(keyValStr[0], keyValStr[1]);
        }
        return result;
    }
}
