package com.lujh.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by lujianhao on 2018/3/5.
 */
public class AccessLogListOut {
    public AccessLogListOut(){}

    private String ip = StringUtils.EMPTY;

    private String useragent = StringUtils.EMPTY;

    private String referer = StringUtils.EMPTY;

    private Integer successNumber;

    private Integer failNumber;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public Integer getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(Integer successNumber) {
        this.successNumber = successNumber;
    }

    public Integer getFailNumber() {
        return failNumber;
    }

    public void setFailNumber(Integer failNumber) {
        this.failNumber = failNumber;
    }
}
