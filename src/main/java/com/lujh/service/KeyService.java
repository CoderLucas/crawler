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

    public Key getByKey(String keyStr) {
        KeyExample example = new KeyExample();
        KeyExample.Criteria criteria = example.createCriteria();
        criteria.andKeystrEqualTo(keyStr);
        List<Key> keyList = keyMapper.selectByExample(example);
        return keyList.size() == 0 ? null : keyList.get(0);
    }

    public List<String> getValueByKey(String keyStr) {
        KeyExample example = new KeyExample();
        KeyExample.Criteria criteria = example.createCriteria();
        criteria.andKeystrEqualTo(keyStr);
        List<Key> keyList = keyMapper.selectByExample(example);
        if (keyList.size() == 0) {
            Key key = new Key();
            key.setKeystr(keyStr);
            key.setValue("");
            keyMapper.insert(key);
            return ListUtil.fromString(key.getValue());
        }
        Key key = keyList.get(0);
        return ListUtil.fromString(key.getValue());
    }

    public List<Key> getAll() {
        return keyMapper.selectByExample(null);
    }
}
