package com.lujh.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujh.bean.Comment;
import com.lujh.service.CommentService;
import com.lujh.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lujianhao on 2018/2/4.
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Msg add(@Valid Comment comment) {
        try {
            commentService.add(comment);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @DeleteMapping
    public Msg delete(@RequestParam(value = "id") Integer id) {
        try {
            commentService.delete(id);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/list")
    public Msg list(@RequestParam(value = "goods_id", defaultValue = "0") Integer goodsId,
                    @RequestParam(value = "pn", defaultValue = "1", required = false) Integer pn,
                    @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        try {
            PageHelper.startPage(pn, size);
            List<Comment> commentList = commentService.getAll(goodsId);
            PageInfo pageInfo = new PageInfo(commentList, 5);
            return Msg.success().add("comment", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }
}
