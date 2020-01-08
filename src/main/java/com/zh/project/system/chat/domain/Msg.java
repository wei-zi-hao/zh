package com.zh.project.system.chat.domain;

import com.zh.project.monitor.online.domain.UserOnline;

import java.io.Serializable;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
public class Msg implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 是否系统
     */
    private Boolean system = false;
    /**
     * 发信人id
     */
    private String id;

    /**
     * 发信人名称
     */
    private String to;

    /**
     * 发信人头像
     */
    private String toAvatar;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 状态（上线/下线标识）
     */
    private String status;

    /**
     * 上线/下线人的头像
     */
    private String statusAvatar;

    /**
     * 上线/下线人的Id （用来移除或者添加到好友面板）
     */
    private String statusId;

    /**
     * 是否已读
     */
    private Integer state;
    /**
     * 在线用户列表
     */
    private List<UserOnline> userOnlineList;

    public Msg() {

    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToAvatar() {
        return toAvatar;
    }

    public void setToAvatar(String toAvatar) {
        this.toAvatar = toAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusAvatar() {
        return statusAvatar;
    }

    public void setStatusAvatar(String statusAvatar) {
        this.statusAvatar = statusAvatar;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<UserOnline> getUserOnlineList() {
        return userOnlineList;
    }

    public void setUserOnlineList(List<UserOnline> userOnlineList) {
        this.userOnlineList = userOnlineList;
    }
}
