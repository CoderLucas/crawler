package com.lujh.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

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

    public static class M implements Comparable<M> {

        private int id;
        private int sort;

        public M(int id, int sort) {
            this.id = id;
            this.sort = sort;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            M m = (M) o;

            return id == m.id;
        }

        @Override
        public int hashCode() {
            int result = id;
            return result;
        }

        @Override
        public int compareTo(M o) {
            if (!this.equals(o)){
                return Integer.valueOf(o.sort).compareTo(this.sort);
            }
            return 0;
        }

        @Override
        public String toString() {
            return "" + id + ":" + sort;
        }
    }

    public static void main(String[] args) {
        Set<M> set = new TreeSet<>();
        set.add(new M(1,1));
        set.add(new M(2,2));
        set.add(new M(3,3));
        set.add(new M(1,3));
        set.add(new M(1,4));

        for (M m : set) {
            System.out.println(m);
        }
    }

}
