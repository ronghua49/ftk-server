package com.risepu.ftk.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public List<String> getStrContainData(String str, String start, String end, boolean isSpecial) {
        List<String> result = new ArrayList<>();
        if (isSpecial) {
            start = "\\" + start;
            end = "\\" + end;
        }
        String regex = start + "(.*?)" + end;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String key = matcher.group(1);
            if (!key.contains(start) && !key.contains(end)) {
                result.add(key);
            }
        }
        return result;

    }

    /**
     * 计算字符串在给定字符串出现的次数
     *
     * @param src 给定字符串
     * @param des 目标字符串
     * @return
     */
    public static int findCount(String src, String des) {
        int index = 0;
        int count = 0;
        while ((index = src.indexOf(des, index)) != -1) {
            count++;
            index = index + des.length();
        }
        return count;
    }

}
