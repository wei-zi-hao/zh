package com.zh.project.system.chat.service;

import com.zh.project.system.chat.domain.ChatApply;
import com.zh.project.system.chat.domain.InitResult;

import java.util.List;

/**
 *  服务层
 *
 * @author eric
 */
public interface ChatService {

    /**获取初始化数据*/
    public InitResult getInitResult();

    /**好友申请*/
    public int friendApply(ChatApply chatApply);

    /**查询好友关系*/
    public int selectFriendByFriendId(String uid);

    /**获取申请消息*/
    public List<ChatApply> selectMsgBoxList();

    /**拒绝好友申请*/
    public int refuseFriend(String uid);

    /**同意好友申请*/
    public int agreeFriend(String uid);

    /**删除好友*/
    public int deleteFriend(String loginName, String userId);
}
