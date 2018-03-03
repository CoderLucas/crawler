package com.lujh.util.enums;

/**
 * Created by lujianhao on 2017/12/28.
 */
public enum ErrorCodeEnum {

    //用户相关 error
    USER_PHONE_ERROR(1000, "手机号错误"),
    USER_PASSWORD_ERROR(1001, "密码为6-20位，不包含符号"),
    USER_PASSWORD_NOT_EQUALS_ERROR(1002, "密码输入不相同，请检查"),
    ;

    int code;
    private String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
