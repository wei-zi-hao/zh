package com.zh.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.zh.framework.config.RuoYiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip-api.com/json/";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (RuoYiConfig.isAddressEnabled()) {
            String rspStr = HttpUtils.sendPost(IP_URL+ip, "lang=zh-CN");
            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            String region = obj.getString("regionName");
            String city = obj.getString("city");
            address = region + " " + city;
        }
        return address;
    }
}
