package com.lujh.util;

/**
 * Created by lujianhao on 2018/4/12.
 */
public class SimpleObject {

    private String key;
    private String value;

    public SimpleObject() {
    }

    public SimpleObject(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
