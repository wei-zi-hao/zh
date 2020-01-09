package com.zh.common.utils.chat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.zh.framework.baidu.ImageApi;
import com.zh.project.system.chat.controller.WsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.websocket.Session;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 百度图片识别工具类
 *
 * @author
 */
public class BaiDuImageUtils {
    private static final Logger log = LoggerFactory.getLogger(BaiDuImageUtils.class);

    //根据本地图片路径识别  并推送
    public static void getImageInfo(String resourePath,String filePath,String loginName) throws IOException {

        BufferedImage image = ImageIO.read(new FileInputStream(new File(filePath)));
        //如果image=null 表示上传的不是图片格式
        if (image == null) {
            pushMsg(resourePath,"图片格式不正确.",loginName);
            return;
        }

        int height = image.getHeight();
        int width = image.getWidth();

        //判断图片是否是在分辨率范围内
        if (height > 4000 || height < 50||width > 4000 || width < 50) {
            pushMsg(resourePath,"图片最短边至少50px，最长边最大4000px",loginName);
            return;
        }
        if (height/width>3 || width/height>3) {
            pushMsg(resourePath,"图片长宽比需在3：1以内.",loginName);
            return;
        }

        final AipImageClassify client = ImageApi.getInstance();
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        HashMap<String, String> option = new HashMap<>();
        option.put("baike_num","5");
        String res = client.advancedGeneral(filePath,option).toString();
        JSONArray result = JSONObject.parseObject(res).getJSONArray("result");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<result.size();i++){
            if(result.getJSONObject(i).getDouble("score")>0.5){
                sb.append(i+1+"、\n");
                sb.append("可信度--"+result.getJSONObject(i).getDouble("score")*100+"%\n");
                sb.append("主题--"+result.getJSONObject(i).getString("root")+"\n");
                sb.append("图片名称--"+result.getJSONObject(i).getString("keyword")+"\n");
                sb.append("百科链接--"+result.getJSONObject(i).getJSONObject("baike_info")
                        .getString("baike_url")+"\n");

                sb.append("图片链接--"+result.getJSONObject(i).getJSONObject("baike_info")
                        .getString("image_url")+"\n");
                sb.append("图片描述--"+result.getJSONObject(i).getJSONObject("baike_info")
                        .getString("description")+"\n\n");
            }
        }
        if(sb.length()==0){
            pushMsg(resourePath,"没有识别出图片信息。",loginName);
            return;
        }
        pushMsg(resourePath,sb.toString(),loginName);
    }
    //推送消息
    public static  void pushMsg(String imageURL,String content,String loginName){
        ConcurrentHashMap<String, Session> mapUS = WsController.getMapUS();
        //推送图片
       String imageText = JSONObject.toJSONString(MsgUtils.getImageInfoMsg("img["+imageURL+"]"));
        mapUS.get(loginName).getAsyncRemote().sendText(imageText);
        //推送图片信息
        String text = JSONObject.toJSONString(MsgUtils.getImageInfoMsg(content));
        mapUS.get(loginName).getAsyncRemote().sendText(text);
    }

}
