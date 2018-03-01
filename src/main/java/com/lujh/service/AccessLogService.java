package com.lujh.service;

import com.lujh.bean.AccessLog;
import com.lujh.dao.AccessLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
