package com.lujh.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianhao on 2018/3/4.
 */
public enum KeyValue {
    ip_limit("ip_limit"),                   // IP限制
    ip_whitelist("ip_whitelist"),           // IP白名单
    ip_blacklist("ip_blacklist"),           // IP黑名单
    ip_status("ip_status"),                 // 开启状态

    useragent_limit("useragent_limit"),     // 浏览器限制
    useragent_status("useragent_status"),   // 开启状态

    referer_limit("referer_limit"),         // 来源限制
    referer_status("referer_status"),       // 开启状态

    user_status("user_status"),             // 开启状态

    code_limit("code_limit"),               // 验证码限制
    code_status("code_status"),             // 开启状态
    ;
    private String value;

    public String getValue() {
        return value;
    }

    KeyValue(String value) {
        this.value = value;
    }

    public static List<KeyValue> getList() {
        List<KeyValue> list = new ArrayList<>();
        for (KeyValue type : KeyValue.values()) {
            list.add(type);
        }
        return list;
    }

    public static KeyValue is(String value) {
        for (KeyValue type : KeyValue.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}
