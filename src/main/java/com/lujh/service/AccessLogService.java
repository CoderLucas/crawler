package com.lujh.service;

import com.lujh.bean.AccessLog;
import com.lujh.bean.AccessLogExample;
import com.lujh.dao.AccessLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lujianhao on 2018/3/1.
 */
@Service
public class AccessLogService {

    @Autowired
    private AccessLogMapper accessLogMapper;

    public void add(AccessLog accessLog) {
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

    public Integer countByIP(String ip, Date from, Date to) {
        AccessLogExample example = accessLogExample(ip, "", "", from, to);
        return (int) accessLogMapper.countByExample(example);
    }

    public Integer countByUserAgent(String userAgent, Date from, Date to) {
        AccessLogExample example = accessLogExample("", userAgent, "", from, to);
        return (int) accessLogMapper.countByExample(example);
    }

    public Integer countByReferer(String referer, Date from, Date to) {
        AccessLogExample example = accessLogExample("", "", referer, from, to);
        return (int) accessLogMapper.countByExample(example);
    }

    private AccessLogExample accessLogExample(String ip, String userAgent, String referer, Date from, Date to) {
        AccessLogExample example = new AccessLogExample();
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
        if (from != null && to != null) {
            criteria.andCreatetimeBetween(from, to);
        }
        return example;
    }


}
