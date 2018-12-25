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


    public static void main(String[] args) {
        StringUtil s = new StringUtil();

        String str = "1${name}567${sdsdg}2345${sdsd}9";
        List<String> result = s.getStrContainData(str, "{", "}", true);
        for (String key : result) {
            System.out.println(key);

        }
    }
}
