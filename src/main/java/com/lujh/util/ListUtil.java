package com.lujh.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lujianhao on 2018/2/6.
 */
public class ListUtil {

    public static List<String> fromString(String string) {
        return fromString(string, ",");
    }

    public static List<String> fromString(String string, String regex) {
        List<String> strList = new ArrayList<>();
        if (string.length() == 0) {
            return strList;
        }
        String[] strs = string.split(regex);
        strList.addAll(Arrays.asList(strs));
        return strList;
    }

    public static void main(String[] args) {
        System.out.print(fromString("100"));
        System.out.print(System.currentTimeMillis());

    }
}
