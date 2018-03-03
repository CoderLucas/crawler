package com.lujh.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianhao on 2018/3/2.
 */
public enum AccessLogEnum {
    FAIL(0),      // 拒绝访问
    SUCCESS(1);     // 请求成功

    private int value;

    public int getValue() {
        return value;
    }

    AccessLogEnum(int value) {
        this.value = value;
    }

    public static List<AccessLogEnum> getList() {
        List<AccessLogEnum> list = new ArrayList<>();
        for (AccessLogEnum type : AccessLogEnum.values()) {
            list.add(type);
        }
        return list;
    }

    public static AccessLogEnum is(int value) {
        for (AccessLogEnum type : AccessLogEnum.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}
