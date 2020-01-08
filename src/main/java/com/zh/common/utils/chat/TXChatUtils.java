package com.zh.common.utils.chat;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * 腾讯智能闲聊工具类
 *
 * @author
 */
public class TXChatUtils {
    private static final Logger log = LoggerFactory.getLogger(TXChatUtils.class);

    private static final String APPID = "2123942455";
    private static final String KEY = "7WjEplTuBWJyLLIz";
    private static final String AIURL = "https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";


    public static String chat(String content,String userId) {
        String result = "";
        int time_stamp = (int) (new Date().getTime() / 1000);
        String nonce_str = getRandomString(16);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("app_id", APPID);
            map.put("time_stamp", time_stamp);
            map.put("nonce_str", nonce_str);
            map.put("session",  userId);
            map.put("question", content);
            map = getSignature(map, KEY);
            String body = paramToStrNotEncode(map);
            String conten = post(AIURL, body);
            JSONObject json = JSONObject.parseObject(conten);
            if (json.getIntValue("ret") == 0) {
                JSONObject data = json.getJSONObject("data");
                result = data.getString("answer");
            }
            log.info("聊天信息：{}", conten);

        } catch (Exception e) {
            result = "聊天出状况啦，待会再试！";
            e.printStackTrace();
        }
        return result;
    }


    public static String getRandomString(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static Map<String, Object> getSignature(Map<String, Object> params, String CONFIG)
            throws IOException {
        Map<String, Object> sortedParams = new TreeMap<>(params);
        Set<Map.Entry<String, Object>> entrys = sortedParams.entrySet();
        StringBuilder baseString = new StringBuilder();
        for (Map.Entry<String, Object> param : entrys) {
            if (param.getValue() != null
                    && !"".equals(param.getKey().trim())
                    && !"sign".equals(param.getKey().trim())
                    && !"".equals(param.getValue())) {
                baseString
                        .append(param.getKey().trim())
                        .append("=")
                        .append(URLEncoder.encode(param.getValue().toString(), "UTF-8"))
                        .append("&");
            }
        }
        if (baseString.length() > 0) {
            baseString.deleteCharAt(baseString.length() - 1).append("&app_key=").append(CONFIG);
        }
        try {
            String sign = new SimpleHash("MD5",baseString.toString().toCharArray()).toString().toUpperCase();
            System.out.println("sign:" + sign);
            sortedParams.put("sign", sign);

        } catch (Exception ex) {
            throw new IOException(ex);
        }
        return sortedParams;
    }

    public static String paramToStrNotEncode(Map<String, Object> params)
            throws UnsupportedEncodingException {
        StringBuffer content = new StringBuffer();
        if (params.isEmpty()) {
            params.put("_NULL", "NULL");
        }
        int i = 0;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue() == null ? "" : entry.getValue().toString();
            value = URLEncoder.encode(value, "UTF8");
            content.append((i == 0 ? "" : "&") + key + "=" + value);
            i++;
        }
        return content.toString();
    }

    public static String post(String urlStr, String body) {
        String response = "";
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Charset", "utf-8");
            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            conn.getOutputStream().write(body.getBytes("UTF-8"));
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            response = buffer.toString();
            in.close();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            ;
        }
        return response;
    }

}
