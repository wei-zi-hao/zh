package com.zh.common.utils.chat;

import com.zh.common.utils.ShiroUtils;
import com.zh.project.system.chat.domain.InitFriend;
import com.zh.project.system.chat.domain.InitGroup;
import com.zh.project.system.chat.domain.InitUser;
import com.zh.project.system.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天初始化工具类
 *
 * @author
 */
public class ChatInitUtils {
    private static final Logger log = LoggerFactory.getLogger(ChatInitUtils.class);

    
    //当前用户信息
    public static InitUser getInitUser(){
        User sysUser = ShiroUtils.getSysUser();

        InitUser initUser = new InitUser();
        initUser.setId(sysUser.getLoginName());
        initUser.setUsername(sysUser.getLoginName());
        initUser.setAvatar(sysUser.getAvatar());
        initUser.setSign(sysUser.getRemark());
        initUser.setStatus("online");

        return initUser;
    }

    //获取我的好友默认组数据
    public static InitFriend getInitFriendGroup(){
        InitFriend initFriend = new InitFriend();
        initFriend.setGroupname("我的好友");
        initFriend.setId(99);
        initFriend.setOnline(1);
        return initFriend;
    }

    //获取系统默认好友组数据
    public static List<InitFriend> getInitDefaultFriend(){
        List<InitFriend> initFriendList= new ArrayList<>();

        InitFriend initFriend0 = new InitFriend();
        initFriend0.setGroupname("系统");
        initFriend0.setId(0);
        initFriend0.setOnline(1);

        InitFriend initFriend1 = new InitFriend();
        initFriend1.setGroupname("在线用户");
        initFriend1.setId(1);
        initFriend1.setOnline(1);

        InitFriend initFriend2 = new InitFriend();
        initFriend2.setGroupname("闲聊");
        initFriend2.setId(2);
        initFriend2.setOnline(2);

        initFriendList.add(initFriend0);
        initFriendList.add(initFriend1);
        initFriendList.add(initFriend2);

        return initFriendList;
    }

    //获取系统默认群组数据
    public static List<InitGroup> getInitDefaultGroup(){
        List<InitGroup> InitGroupList= new ArrayList<>();

        InitGroup InitGroup0 = new InitGroup();
        InitGroup0.setGroupname("在线用户组");
        InitGroup0.setId(101);
        InitGroup0.setAvatar("/img/chat/group.gif");

        InitGroupList.add(InitGroup0);
        return InitGroupList;
    }
}
