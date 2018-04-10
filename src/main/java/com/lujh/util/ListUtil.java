package com.lujh.util;

import org.apache.commons.lang3.StringUtils;

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

    /**
     * @param list
     * @return
     */
    public static String fromList(List<String> list) {
        return StringUtils.join(list.toArray(), ",");
    }

    public static void main(String[] args) {

        String list = null;
        list.equals("ddd");
    }
}
