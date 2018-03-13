package com.lujh.util;

/**
 * Created by lujianhao on 2018/3/13.
 */
public class Wrapper<T> {
    private T value;

    public Wrapper() {
    }

    public Wrapper(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }
}
