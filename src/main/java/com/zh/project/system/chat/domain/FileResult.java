package com.zh.project.system.chat.domain;

import java.io.Serializable;

/**
 * 聊天文件返回信息体
 *
 * @author EricWei on 2019/11/8
 */
public class FileResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code = 0;

    private String msg;

    private Integer count = 0;

    private Object data;


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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public FileResult() {

    }

    public FileResult(String filePath) {
        this.data = new FileResultData(filePath);
    }

    public FileResult(String filePath, String fileName) {
        this.data = new FileResultData(filePath, fileName);
    }

    public FileResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回成功消息
     *
     * @param filePath 文件路径
     * @return 成功消息
     */
    public static FileResult success(String filePath, String fileName) {
        return new FileResult(filePath, fileName);
    }

    /**
     * 返回成功消息
     *
     * @param filePath 文件路径
     * @return 成功消息
     */
    public static FileResult success(String filePath) {
        return new FileResult(filePath);
    }


    /**
     * 返回消息
     */
    public static FileResult error(String msg) {
        return new FileResult(1, msg);
    }


}
