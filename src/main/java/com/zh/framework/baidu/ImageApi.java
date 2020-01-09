package com.zh.framework.baidu;

import com.baidu.aip.imageclassify.AipImageClassify;

/**
 * @author EricWei on 2020/1/8
 */
public class ImageApi {
   private static AipImageClassify aipImageClassify;
    //设置APPID/AK/SK
    public static final String APP_ID = "";
    public static final String API_KEY = "";
    public static final String SECRET_KEY = "";
    public  static  AipImageClassify getInstance(){
        if(aipImageClassify == null) {
            synchronized(AipImageClassify.class){
                if(aipImageClassify == null) {
                    aipImageClassify = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
                }
            }
        }
        return aipImageClassify;
    }
}
