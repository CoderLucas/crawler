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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/ipwhitelist/delete")
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

    @GetMapping(value = "/ipwhitelist")
    public Msg ipWhiteList(@RequestParam(value = "pn", defaultValue = "1", required = false) Integer pn,
                           @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        try {

            PageHelper.startPage(pn, size);
            List<String> ipList = keyService.getValueByKey(KeyValue.ip_whitelist.getValue());
            PageInfo pageInfo = new PageInfo(ipList, 5);
            return Msg.success().add("pageInfo", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @PostMapping(value = "/ipblacklist/add")
    public Msg ipBlackListAdd(@RequestParam(value = "ip") String ip) {
        try {
            List<String> ipBlackList = keyService.getValueByKey(KeyValue.ip_blacklist.getValue());
            if (!ipBlackList.contains(ip)) {
                ipBlackList.add(ip);
                String ipString = ListUtil.fromList(ipBlackList);
                Key key = keyService.getByKey(KeyValue.ip_blacklist.getValue());
                key.setValue(ipString);
                keyService.update(key);
            }
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @PostMapping(value = "/ipblacklist/delete")
    public Msg ipBlackListDelete(@RequestParam(value = "ip") String ip) {
        try {
            List<String> ipBlackList = keyService.getValueByKey(KeyValue.ip_blacklist.getValue());
            if (ipBlackList.contains(ip)) {
                ipBlackList.remove(ip);
                String ipString = ListUtil.fromList(ipBlackList);
                Key key = keyService.getByKey(KeyValue.ip_blacklist.getValue());
                key.setValue(ipString);
                keyService.update(key);
            }
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/ipblacklist")
    public Msg ipBlackList(@RequestParam(value = "pn", defaultValue = "1", required = false) Integer pn,
                           @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        try {

            PageHelper.startPage(pn, size);
            List<String> ipList = keyService.getValueByKey(KeyValue.ip_blacklist.getValue());
            PageInfo pageInfo = new PageInfo(ipList, 5);
            return Msg.success().add("pageInfo", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @PostMapping(value = "/config/set")
    public Msg configSet(@RequestParam(value = "ip_status") String ipStatus,
                         @RequestParam(value = "ip_limit") String ipLimit,
                         @RequestParam(value = "useragent_status") String agentStatus,
                         @RequestParam(value = "useragent_limit") String agentLimit,
                         @RequestParam(value = "referer_status") String refererStatus,
                         @RequestParam(value = "referer_limit") String refererLimit) {
        try {
            Key ipStatusKey = keyService.getByKey("ip_status");
            ipStatusKey.setValue(ipStatus);
            keyService.update(ipStatusKey);

            Key ipLimitKey = keyService.getByKey("ip_limit");
            ipLimitKey.setValue(ipLimit);
            keyService.update(ipLimitKey);

            Key agentStatusKey = keyService.getByKey("useragent_status");
            agentStatusKey.setValue(agentStatus);
            keyService.update(agentStatusKey);

            Key agentLimitKey = keyService.getByKey("useragent_limit");
            agentLimitKey.setValue(agentLimit);
            keyService.update(agentLimitKey);

            Key refererStatusKey = keyService.getByKey("referer_status");
            refererStatusKey.setValue(refererStatus);
            keyService.update(refererStatusKey);

            Key refererLimitKey = keyService.getByKey("referer_limit");
            refererLimitKey.setValue(refererLimit);
            keyService.update(refererLimitKey);

            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/config/set")
    public Msg getConfigSet() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("ip_status", keyService.getByKey("ip_status").getValue());
            map.put("ip_limit", keyService.getByKey("ip_limit").getValue());
            map.put("useragent_status", keyService.getByKey("useragent_status").getValue());
            map.put("useragent_limit", keyService.getByKey("useragent_limit").getValue());
            map.put("referer_status", keyService.getByKey("referer_status").getValue());
            map.put("referer_limit", keyService.getByKey("referer_limit").getValue());

            return Msg.success().add("config", map);
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
