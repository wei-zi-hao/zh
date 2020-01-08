package com.zh.common.utils.chat;

import com.alibaba.fastjson.JSONObject;
import com.zh.project.monitor.online.domain.UserOnline;
import com.zh.project.system.chat.domain.Msg;
import com.zh.project.system.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 返回信息体工具类
 *
 * @author
 */
public class MsgUtils {
    private static final Logger log = LoggerFactory.getLogger(MsgUtils.class);

    //消息盒子消息
    public static Msg getBoxMsg(){
        Msg msg = new Msg();
        msg.setId("消息盒子");
        return msg;
    }

    //消息盒子消息
    public static Msg getBoxMsg(User user,String type){
        Msg msg = new Msg();
        msg.setId("消息盒子");
        msg.setTo(user.getLoginName());
        msg.setToAvatar(user.getAvatar());
        msg.setContent(user.getRemark());
        msg.setStatus(type);
        return msg;
    }

    //上线消息
    public static Msg getOnlineMsg(String loginName, int size, List<UserOnline> userOnlines){
        Msg msg = new Msg();
        msg.setId("消息小助手");
        msg.setContent("用户" + loginName + "进入了聊天室,当前在线人数为:" + size);
        msg.setTo("消息小助手");
        msg.setToAvatar("/img/chat/msg.gif");
        msg.setStatus("on");
        msg.setUserOnlineList(userOnlines);
        return msg;
    }

    //下线消息
    public static Msg getOfflineMsg(String loginName, int size){
        Msg msg = new Msg();
        msg.setId("消息小助手");
        msg.setContent("用户" + loginName + "离开了聊天室,当前在线人数为:" + size);
        msg.setTo("消息小助手");
        msg.setToAvatar("/img/chat/msg.gif");
        msg.setStatus("off");
        msg.setStatusId(loginName);
        return msg;
    }
    
    //用户不在线提醒消息
    public static Msg getUserOfflineMsg(String loginName){
        Msg msg = new Msg();
        msg.setSystem(true);
        msg.setId(loginName);
        msg.setContent("用户["+loginName+"]不在线上！ 已为你存入聊天记录，对方上线后通知");
        return msg;
    }

    //胡歌智能回复信息体
    public static Msg getHgMsg(JSONObject mine){
        Msg hgMsg = new Msg();
        hgMsg.setId("胡歌");
        hgMsg.setTo("胡歌");
        hgMsg.setToAvatar("/img/chat/hg.jpg");
        hgMsg.setState(1);
        //设置回复信息
        hgMsg.setContent(TXChatUtils.chat(mine.getString("content").replace("face", ""),mine.getString("id")));
        return hgMsg;
    }

    //正常回复信息体
    public static Msg getMsg(JSONObject mine){
        Msg msg = new Msg();
        msg.setId(mine.getString("id"));
        msg.setContent(mine.getString("content"));
        msg.setTo(mine.getString("username"));
        msg.setToAvatar(mine.getString("avatar"));
        return msg;
    }

}
