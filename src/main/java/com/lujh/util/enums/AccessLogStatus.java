package com.lujh.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianhao on 2018/3/2.
 */
public enum AccessLogStatus {
    FAIL(0),      // 拒绝访问
    SUCCESS(1);     // 请求成功

    private int value;

    public int getValue() {
        return value;
    }

    AccessLogStatus(int value) {
        this.value = value;
    }

    public static List<AccessLogStatus> getList() {
        List<AccessLogStatus> list = new ArrayList<>();
        for (AccessLogStatus type : AccessLogStatus.values()) {
            list.add(type);
        }
        return list;
    }

    public static AccessLogStatus is(int value) {
        for (AccessLogStatus type : AccessLogStatus.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}
