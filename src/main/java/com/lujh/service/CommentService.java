package com.lujh.service;

import com.lujh.bean.Comment;
import com.lujh.bean.CommentExample;
import com.lujh.bean.UserExample;
import com.lujh.dao.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lujianhao on 2018/2/3.
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public void add(Comment comment) {
        commentMapper.insert(comment);
    }

    public void delete(Integer id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    public void update(Comment comment) {
        commentMapper.updateByPrimaryKey(comment);
    }

    public Comment get(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    public List<Comment> getAll(Integer goodsId) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsidEqualTo(goodsId);
        return commentMapper.selectByExample(example);
    }

    public int count(Integer id) {
        return 1;
    }
}
