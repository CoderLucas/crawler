package com.lujh.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujh.bean.Key;
import com.lujh.service.KeyService;
import com.lujh.util.ListUtil;
import com.lujh.util.Msg;
import com.lujh.util.enums.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lujianhao on 2018/2/4.
 */
@RestController
@RequestMapping(value = "/key")
public class KeyController {

    @Autowired
    private KeyService keyService;

    @PostMapping(value = "/ipwhitelist/add")
    public Msg ipWhiteListAdd(@RequestParam(value = "ip") String ip) {
        try {
            List<String> ipWhiteList = keyService.getValueByKey(KeyValue.ip_whitelist.getValue());
            if (!ipWhiteList.contains(ip)) {
                ipWhiteList.add(ip);
                String ipString = ListUtil.fromList(ipWhiteList);
                Key key = keyService.getByKey(KeyValue.ip_whitelist.getValue());
                key.setValue(ipString);
                keyService.update(key);
            }
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @DeleteMapping(value = "/ipwhitelist/delete")
    public Msg ipWhiteListDelete(@RequestParam(value = "ip") String ip) {
        try {
            List<String> ipWhiteList = keyService.getValueByKey(KeyValue.ip_whitelist.getValue());
            if (ipWhiteList.contains(ip)) {
                ipWhiteList.remove(ip);
                String ipString = ListUtil.fromList(ipWhiteList);
                Key key = keyService.getByKey(KeyValue.ip_whitelist.getValue());
                key.setValue(ipString);
                keyService.update(key);
            }
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @PostMapping
    public Msg add(@Valid Key key) {
        try {
            keyService.add(key);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @DeleteMapping
    public Msg delete(@RequestParam(value = "id") Integer id) {
        try {
            keyService.delete(id);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @PutMapping
    public Msg update(@Valid Key key) {
        try {
            keyService.update(key);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping
    public Msg single(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        try {
            Key key = keyService.get(id);
            return Msg.success().add("key", key);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/list")
    public Msg list(@RequestParam(value = "pn", defaultValue = "1", required = false) Integer pn,
                    @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        try {
            PageHelper.startPage(pn, size);
            List<Key> keyList = keyService.getAll();
            PageInfo pageInfo = new PageInfo(keyList, 5);
            return Msg.success().add("key", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

}
