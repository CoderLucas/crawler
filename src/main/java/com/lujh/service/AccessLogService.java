package com.lujh.service;

import com.lujh.bean.AccessLog;
import com.lujh.bean.AccessLogExample;
import com.lujh.dao.AccessLogMapper;
import com.lujh.util.enums.AccessLogStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lujianhao on 2018/3/1.
 */
@Service
public class AccessLogService {
    @Autowired
    private AccessLogMapper accessLogMapper;

    public void add(AccessLog accessLog) {
        if (accessLog.getReferer() == null) {
            accessLog.setReferer("");
        }
        accessLogMapper.insert(accessLog);
    }

    public void delete(Integer id) {
        accessLogMapper.deleteByPrimaryKey(id);
    }

    public void update(AccessLog accessLog) {
        accessLogMapper.updateByPrimaryKey(accessLog);
    }

    public AccessLog get(Integer id) {
        return accessLogMapper.selectByPrimaryKey(id);
    }

    public Integer countByIP(String ip, AccessLogStatus status, Date from, Date to) {
        AccessLogExample example = new AccessLogExample();
        example = accessLogExample(example, ip, "", "", status, from, to);
        return (int) accessLogMapper.countByExample(example);
    }

    public Integer countByUserAgent(String userAgent, AccessLogStatus status, Date from, Date to) {
        AccessLogExample example = new AccessLogExample();
        example = accessLogExample(example, "", userAgent, "", status, from, to);
        return (int) accessLogMapper.countByExample(example);
    }

    public Integer countByReferer(String referer, AccessLogStatus status, Date from, Date to) {
        AccessLogExample example = new AccessLogExample();
        example = accessLogExample(example, "", "", referer, status, from, to);
        return (int) accessLogMapper.countByExample(example);
    }

    private AccessLogExample accessLogExample(AccessLogExample example, String ip, String userAgent, String referer, AccessLogStatus status, Date from, Date to) {
        AccessLogExample.Criteria criteria = example.createCriteria();
        if (ip.length() > 0) {
            criteria.andIpEqualTo(ip);
        }
        if (userAgent.length() > 0) {
            criteria.andUseragentEqualTo(userAgent);
        }
        if (referer.length() > 0) {
            criteria.andRefererEqualTo(referer);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status.getValue());
        }
        if (from != null && to != null) {
            criteria.andCreatetimeBetween(from, to);
        }
        return example;
    }

    public List<String> listByIP(Date from, Date to) {
        AccessLogExample example = new AccessLogExample();
        AccessLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreatetimeGreaterThan(from);
        criteria.andCreatetimeLessThanOrEqualTo(to);
        return accessLogMapper.groupByIP(example);
    }

    public List<String> listByUserAgent(Date from, Date to) {
        AccessLogExample example = new AccessLogExample();
        AccessLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreatetimeGreaterThan(from);
        criteria.andCreatetimeLessThanOrEqualTo(to);
        return accessLogMapper.groupByUserAgent(example);
    }

    public List<String> listByReferer(Date from, Date to) {
        AccessLogExample example = new AccessLogExample();
        AccessLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreatetimeGreaterThan(from);
        criteria.andCreatetimeLessThanOrEqualTo(to);
        return accessLogMapper.groupByReferer(example);
    }





}
