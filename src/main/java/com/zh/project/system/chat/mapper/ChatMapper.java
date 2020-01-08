package com.zh.project.system.chat.mapper;

import com.zh.project.system.chat.domain.ChatApply;
import com.zh.project.system.chat.domain.InitUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据层
 *
 * @author ruoyi
 */
public interface ChatMapper {

    /**
     * 获取好友组
     */
    public List<InitUser> getInitFriendGroup(String userId);

    /**
     * 添加好友申请
     */
    public int friendApply(ChatApply chatApply);

    /**
     * 添加好友申请
     * @param userId 当前用户ID
     * @param friendId  朋友ID
     */
    public int selectFriendByFriendId(
            @Param("userId") String userId, @Param("friendId") String friendId);

    /**
     * 查询是否首次发送好友申请
     */
    public int selectFirstApply(ChatApply chatApply);


    /**获取申请消息*/
    public List<ChatApply> selectMsgBoxList(String loginName);

    /**拒绝好友申请*/
    public int refuseFriend(ChatApply chatApply);

    /**拒绝好友申请*/
    public int agreeFriend(ChatApply chatApply);

    /**修改好友申请状态*/
    public void updateChatApplyType(ChatApply chatApply);

    /**添加好友关系*/
    public void addFriend(ChatApply chatApply);

    /**删除好友*/
    public int deleteFriend( @Param("loginName") String loginName,  @Param("userId") String userId);

    /**添加删除好友的消息*/
    public void addDeleteFriendApply(ChatApply chatApply);

    /**获取群员*/
    public  List<InitUser> getMembers();

    /**查询未读申请消息*/
    public List<ChatApply> selectUnReadMsgBoxList(String loginName);
}