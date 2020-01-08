package com.zh.project.system.chat.domain;

import java.io.Serializable;

/**
 * 即时聊天初始化
 * @author eric
 */
public class InitResult implements Serializable {
   private static final long serialVersionUID = 1L;

   private int code =0;

   private String msg ="success";
   
   private InitData data;

   public int getCode() {
      return code;
   }

   public void setCode(int code) {
      this.code = code;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public InitData getData() {
      return data;
   }

   public void setData(InitData data) {
      this.data = data;
   }
}
