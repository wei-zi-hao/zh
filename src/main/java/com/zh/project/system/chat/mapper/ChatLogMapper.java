package com.zh.project.system.chat.mapper;

import com.zh.project.system.chat.domain.ChatLog;
import com.zh.project.system.chat.domain.GroupMsg;

import java.util.List;

/**
 * 参数配置 数据层
 *
 * @author ruoyi
 */
public interface ChatLogMapper {

    /**
     * 查询聊天记录
     */
    public List<ChatLog> selectChatLogByFriend(String id, String toid);

    /**
     * 查询未读聊天记录
     */
    public List<ChatLog> getUnReadMessageUrl(String loginName);

    /**
     * 查询未读聊天记录后修改状态
     */
    public void updateState (String loginName);
    /**
     * 插入聊天记录
     */
    public void insertChatLog(ChatLog chatLog);

    /**
     * 插入群组聊天记录
     */
    public void insertGroupChatLog(GroupMsg groupMsg);

    /**
     * 删除一个月前的聊天记录
     */
    public  void deleteChatlog();

    /**
     * 查询群组聊天记录
     */
    public  List<ChatLog> selectChatLogByGroup();
}