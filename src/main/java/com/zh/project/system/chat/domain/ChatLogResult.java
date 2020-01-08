package com.zh.project.system.chat.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 聊天记录返回信息体
 *
 * @author EricWei on 2019/11/8
 */
public class ChatLogResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code = 0;

    private String msg;

    private List<ChatLog> data;

    public ChatLogResult(){

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ChatLog> getData() {
        return data;
    }

    public void setData(List<ChatLog> data) {
        this.data = data;
    }
}
