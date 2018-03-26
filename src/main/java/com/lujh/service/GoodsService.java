package com.lujh.service;

import com.lujh.bean.Goods;
import com.lujh.bean.GoodsExample;
import com.lujh.dao.GoodsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lujianhao on 2018/2/3.
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }

    public void delete(Integer id) {
        goodsMapper.deleteByPrimaryKey(id);
    }

    public void update(Goods goods) {
        goodsMapper.updateByPrimaryKey(goods);
    }

    public Goods get(Integer id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    public List<Goods> getAll(String name, Integer type) {
        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type > 0) {
            criteria.andTypeEqualTo((byte) type.intValue());
        }
        return goodsMapper.selectByExample(example);
    }
}
