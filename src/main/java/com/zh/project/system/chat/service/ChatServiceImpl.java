package com.zh.project.system.chat.service;


import com.zh.common.utils.ShiroUtils;
import com.zh.common.utils.chat.ChatInitUtils;
import com.zh.project.system.chat.domain.*;
import com.zh.project.system.chat.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 参数配置 服务层实现
 *
 * @author ruoyi
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;




    @Override
    public InitResult getInitResult() {

        InitResult initResult = new InitResult();

        InitData initData = new InitData();
        //设置当前用户属性
        initData.setMine(ChatInitUtils.getInitUser());
        try{
            //查询好友
            InitFriend initFriendGroup = ChatInitUtils.getInitFriendGroup();
            List<InitUser> InitUserList = chatMapper.getInitFriendGroup(ShiroUtils.getLoginName());
            initFriendGroup.setList(InitUserList);
            // 设置默认好友组
            List<InitFriend> initDefaultFriendGroup = ChatInitUtils.getInitDefaultFriend();
            //添加好友
            initDefaultFriendGroup.add(initFriendGroup);
            //设置默认朋友
            initData.setFriend(initDefaultFriendGroup);
            //设置默认群组
            initData.setGroup(ChatInitUtils.getInitDefaultGroup());

            //设置初始化数据
            initResult.setData(initData);
            return initResult;
        }catch (Exception e){
            initResult.setCode(1);
            initResult.setMsg(e.getMessage());
            return initResult;
        }

    }

    @Override
    public int friendApply(ChatApply chatApply) {
        chatApply.setContent("申请添加你为好友");
        chatApply.setType(0);
        return  chatMapper.friendApply(chatApply);
    }

    @Override
    public int selectFriendByFriendId(String uid) {
        return chatMapper.selectFriendByFriendId(ShiroUtils.getLoginName(),uid);
    }

    @Override
    public List<ChatApply> selectMsgBoxList() {
        return  chatMapper.selectMsgBoxList(ShiroUtils.getLoginName());
    }

    @Override
    @Transactional
    public int refuseFriend(String uid) {
        String userName = ShiroUtils.getLoginName();
        ChatApply chatApply = new ChatApply();
        chatApply.setFromId(userName);
        chatApply.setToId(uid);
        chatApply.setType(3);
        chatApply.setContent("【"+ShiroUtils.getLoginName()+"】拒绝了你的好友申请");
        chatMapper.updateChatApplyType(chatApply);
        chatApply.setFromId("");
        chatMapper.refuseFriend(chatApply);

        ChatApply chatApplyOwn = new ChatApply();
        chatApplyOwn.setToId(userName);
        chatApplyOwn.setType(2);
        chatApplyOwn.setContent("你拒绝了【"+uid+"】的好友申请");
        return chatMapper.refuseFriend(chatApplyOwn);
    }

    @Override
    @Transactional
    public int agreeFriend(String uid) {
        String userName = ShiroUtils.getLoginName();
        ChatApply chatApply = new ChatApply();
        chatApply.setFromId(userName);
        chatApply.setToId(uid);
        chatApply.setType(2);
        chatApply.setContent("【"+ShiroUtils.getLoginName()+"】同意了你的好友申请");

        chatMapper.addFriend(chatApply);
        chatMapper.updateChatApplyType(chatApply);
        chatApply.setFromId("");
        chatMapper.agreeFriend(chatApply);

        ChatApply chatApplyOwn = new ChatApply();
        chatApplyOwn.setToId(userName);
        chatApplyOwn.setType(2);
        chatApplyOwn.setContent("你同意了【"+uid+"】的好友申请");
        return chatMapper.agreeFriend(chatApplyOwn);
    }

    @Override
    @Transactional
    public int deleteFriend(String loginName, String userId) {
        int row = chatMapper.deleteFriend(loginName, userId);

        ChatApply chatApply = new ChatApply();
        chatApply.setType(3);
        chatApply.setToId(loginName);
        chatApply.setContent("你解除了与【"+userId+"】的好友关系");
        chatMapper.addDeleteFriendApply(chatApply);
        chatApply.setToId(userId);
        chatApply.setContent("【"+loginName+"】解除了与你的好友关系");
        chatMapper.addDeleteFriendApply(chatApply);

        return row;
    }


}
