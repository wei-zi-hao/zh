package com.zh.project.system.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.common.utils.SpringUtils;
import com.zh.common.utils.chat.MsgUtils;
import com.zh.framework.manager.AsyncManager;
import com.zh.framework.manager.factory.AsyncFactory;
import com.zh.project.monitor.online.domain.UserOnline;
import com.zh.project.monitor.online.mapper.UserOnlineMapper;
import com.zh.project.system.chat.domain.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单聊
 *
 * @author EricWei on 2019/11/5
 */
@ServerEndpoint(value = "/single/{loginName}")
@Component
public class WsController {

    private static final Logger logger = LoggerFactory.getLogger(WsController.class);

    //存放用户和session
    private static ConcurrentHashMap<String, Session> mapUS = new ConcurrentHashMap<String, Session>();
    private static ConcurrentHashMap<Session, String> mapSU = new ConcurrentHashMap<Session, String>();

    public static ConcurrentHashMap<String, Session> getMapUS(){
        return mapUS;
    }
    public  static ConcurrentHashMap<Session, String> getMapSU(){
        return mapSU;
    }


    /**连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("loginName") String loginName) {
        //更新用户在线状态
        mapUS.put(loginName, session);
        mapSU.put(session, loginName);

        //查询上线用户的信息
        UserOnlineMapper userOnlineMapper = SpringUtils.getBean(UserOnlineMapper.class);
        UserOnline userOnline = new UserOnline();
        List<UserOnline> userOnlines = userOnlineMapper.selectUserOnlineList(userOnline);

        //上线提醒信息
        Msg onlineMsg = MsgUtils.getOnlineMsg(loginName, mapUS.size(), userOnlines);
        String text = JSONObject.toJSONString(onlineMsg);

        //群发给在线用户
        for (String value : mapSU.values()) {
            try {
                    mapUS.get(value).getBasicRemote().sendText(text);
            } catch (IOException e) {
            }
        }

        logger.info("用户" + loginName + "进入了聊天室,当前在线人数为" + mapUS.size());
    }


    /**连接关闭调用的方法*/
    @OnClose
    public void onClose(Session session) {
        String loginName = mapSU.get(session);
        if (loginName != null && loginName != "") {

            //更新用户在线状态
            mapUS.remove(loginName);
            mapSU.remove(session);
            logger.info("用户" + loginName + "离开了聊天室,当前在线人数为" + mapUS.size());

            //新建系统消息
            Msg offLineMsg = MsgUtils.getOfflineMsg(loginName, mapUS.size());
            String text = JSONObject.toJSONString(offLineMsg);

            //群发给在线用户
            for (String value : mapSU.values()) {
                try {
                    mapUS.get(value).getBasicRemote().sendText(text);
                } catch (IOException e) {
                }
            }
        }
    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        String loginName = mapSU.get(session);
        if (loginName != null && loginName != "") {
            //更新用户在线状态
            mapUS.remove(loginName);
            mapSU.remove(session);
            logger.info("用户" + loginName + "退出！当前在线人数为" + mapUS.size());
        }
        logger.error("single发生错误!");
        error.printStackTrace();
    }

    /** 收到客户端消息后调用的方法*/
    @OnMessage
    public void onMessage(String message, Session session) {
        //mine是发送人信息  to是收件人信息
        JSONObject messageObject = JSONObject.parseObject(message);
        JSONObject mine = messageObject.getJSONObject("mine");
        JSONObject to = messageObject.getJSONObject("to");

        //**详细内容*/
        Msg msg = MsgUtils.getMsg(mine);
        msg.setState(1);
        String text = JSONObject.toJSONString(msg);

        Msg hgMsg = null;

        //群组消息
        if(to.getString("id").equals("101")){
            //群发给在线群用户
            msg.setStatus("group");
            String groupText = JSONObject.toJSONString(msg);

            for (String value : mapSU.values()) {
                try {
                    if(!value.equals(msg.getId())){
                        mapUS.get(value).getBasicRemote().sendText(groupText);
                    }
                } catch (IOException e) {
                }
            }
            //存储群信息
            AsyncManager.me().execute(AsyncFactory.recordGroupChatLog(msg));
            return;


        //发送智能回复
        }

        if (to.getString("id").equals("胡歌")) {
            hgMsg = MsgUtils.getHgMsg(mine);
            String AItext = JSONObject.toJSONString(hgMsg);
            mapUS.get(mine.getString("id")).getAsyncRemote().sendText(AItext);

            //用户在线就发送
        }else if (mapUS.containsKey(to.getString("id"))) {
            msg.setState(1);//已读
            mapUS.get(to.getString("id")).getAsyncRemote().sendText(text);

            //用户不在线就系统提示
        } else {
            msg.setState(0);//未读
            Msg userOfflineMsg = MsgUtils.getUserOfflineMsg(to.getString("id"));
            String offLineText = JSONObject.toJSONString(userOfflineMsg);
            mapUS.get(mine.getString("id")).getAsyncRemote().sendText(offLineText);
        }

        //**在不在线都存储聊天记录*/
        AsyncManager.me().execute(AsyncFactory.recordChatLog(msg, to.getString("id")));

        //记录胡歌的回复
        if(hgMsg != null){
            AsyncManager.me().execute(AsyncFactory.recordChatLog(hgMsg, mine.getString("id")));
        }

    }

}