package com.lujh.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujh.bean.Goods;
import com.lujh.bean.GoodsListOut;
import com.lujh.bean.GoodsOut;
import com.lujh.service.GoodsService;
import com.lujh.util.IdCodeUtil;
import com.lujh.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianhao on 2018/2/4.
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping
    public Msg add(@Valid Goods goods) {
        try {
            goodsService.add(goods);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @DeleteMapping
    public Msg delete(@RequestParam(value = "id") Integer id) {
        try {
            goodsService.delete(id);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @PutMapping
    public Msg update(@Valid Goods goods) {
        try {
            goodsService.update(goods);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/single")
    public Msg get(@RequestParam(value = "id") String eid) {
        try {
            Goods goods = goodsService.get(IdCodeUtil.decode(eid));
            GoodsOut goodsOut = new GoodsOut(goods);
            return Msg.success().add("goods", goodsOut);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/list")
    public Msg list(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                    @RequestParam(value = "type", defaultValue = "-1",required = false) Integer type,
                    @RequestParam(value = "pn", defaultValue = "1", required = false) Integer pn,
                    @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        try {
            PageHelper.startPage(pn, size);
            List<Goods> goodsList = goodsService.getAll(name, type);
            List<GoodsListOut> goodsListOutList = new ArrayList<>();
            goodsList.forEach(goods -> goodsListOutList.add(new GoodsListOut(goods)));
            PageInfo pageInfo = new PageInfo(goodsListOutList, 5);
            return Msg.success().add("goods", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }
}
