package com.lujh.service;

import com.lujh.bean.Key;
import com.lujh.bean.KeyExample;
import com.lujh.dao.KeyMapper;
import com.lujh.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lujianhao on 2018/2/3.
 */
@Service
public class KeyService {

    @Autowired
    private KeyMapper keyMapper;

    public void add(Key key) {
        keyMapper.insert(key);
    }

    public void delete(Integer id) {
        keyMapper.deleteByPrimaryKey(id);
    }

    public void update(Key key) {
        keyMapper.updateByPrimaryKey(key);
    }

    public Key get(Integer id) {
        return keyMapper.selectByPrimaryKey(id);
    }

    public List<String> getValueByKey(String keyStr) {
        KeyExample example = new KeyExample();
        KeyExample.Criteria criteria = example.createCriteria();
        criteria.andKeystrEqualTo(keyStr);
        Key key = keyMapper.selectByExample(example).get(0);
        return ListUtil.fromString(key.getValue());
    }

    public List<Key> getAll() {
        return keyMapper.selectByExample(null);
    }
}
