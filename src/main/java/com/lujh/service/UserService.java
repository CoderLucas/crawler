package com.lujh.service;

import com.lujh.bean.User;
import com.lujh.bean.UserExample;
import com.lujh.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lujianhao on 2018/2/3.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void add(User user) {
        userMapper.insert(user);
    }

    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    public User get(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User getByPhone(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public List<User> getAll() {
        return userMapper.selectByExample(null);
    }
}
