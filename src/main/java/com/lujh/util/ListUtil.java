package com.lujh.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lujianhao on 2018/2/6.
 */
public class ListUtil {

    public static List<String> toList(String str) {
        String strs[] = str.split(",");
        List<String> strList = new ArrayList<>();
        strList.addAll(Arrays.asList(strs));
        return strList;
    }
}
