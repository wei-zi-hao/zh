package com.zh.project.system.redis.controller;


import com.zh.common.utils.RedisUtils;
import com.zh.framework.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/redis")
public class RedisController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(RedisController.class);


    @Autowired
    private RedisUtils redisUtils;

    /**
     *
     */
    @RequestMapping("/set")
    @ResponseBody
    public String set(String k,String v) {
        boolean set = redisUtils.set(k, v);
        return "ok";
    }
    /**
     *
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String k) {
         redisUtils.del(k);

        return "ok";
    }
    @RequestMapping("/get")
    @ResponseBody
    public String get(String k) {
        String o = (String) redisUtils.get(k);
        return o;
    }

}
