package com.lujh.bean;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

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

    private String fromTime;

    private String toTime;

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

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
