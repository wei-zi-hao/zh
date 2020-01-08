package com.zh.project.system.chat.domain;

import java.io.Serializable;

/**
 * FileResult内部对象
 *
 * @author EricWei on 2019/11/8
 */
public class FileResultData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String src;

    private  String name;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public FileResultData(String src) {
        this.src = src;

    }

    public FileResultData(String src,String name) {
        this.src = src;
        this.name = name;
    }
}
