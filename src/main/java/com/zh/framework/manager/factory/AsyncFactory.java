package com.zh.framework.manager.factory;


import com.zh.common.constant.Constants;
import com.zh.common.utils.*;
import com.zh.common.utils.chat.BaiDuImageUtils;
import com.zh.project.monitor.logininfor.domain.Logininfor;
import com.zh.project.monitor.logininfor.service.LogininforServiceImpl;
import com.zh.project.monitor.online.domain.OnlineSession;
import com.zh.project.monitor.online.domain.UserOnline;
import com.zh.project.monitor.online.service.IUserOnlineService;
import com.zh.project.monitor.operlog.domain.OperLog;
import com.zh.project.monitor.operlog.service.IOperLogService;
import com.zh.project.system.chat.domain.ChatLog;
import com.zh.project.system.chat.domain.GroupMsg;
import com.zh.project.system.chat.domain.Msg;
import com.zh.project.system.chat.mapper.ChatLogMapper;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 * 
 * @author liuhulu
 *
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 同步session到数据库
     *
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                UserOnline online = new UserOnline();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setAvatar(session.getAvatar());
                online.setSign(session.getSign());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                online.setSession(session);
                SpringUtils.getBean(IUserOnlineService.class).saveOnline(online);

            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(IOperLogService.class).insertOperlog(operLog);
            }
        };
    }

    /**
     * 记录登陆信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask()
        {
            @Override
            public void run()
            {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                Logininfor logininfor = new Logininfor();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status))
                {
                    logininfor.setStatus(Constants.SUCCESS);
                }
                else if (Constants.LOGIN_FAIL.equals(status))
                {
                    logininfor.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(LogininforServiceImpl.class).insertLogininfor(logininfor);
            }
        };
    }


    /**
     * 记录聊天记录
     * @return 任务task
     */
    public static TimerTask recordChatLog(final Msg msg,final String toid)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                ChatLog chatLog = new ChatLog();
                chatLog.setId(msg.getId());
                chatLog.setUsername(msg.getId());
                chatLog.setAvatar(msg.getToAvatar());
                chatLog.setContent(msg.getContent());
                chatLog.setToid(toid);
                chatLog.setState(msg.getState());
                chatLog.setTimestamp(System.currentTimeMillis());
                SpringUtils.getBean(ChatLogMapper.class).insertChatLog(chatLog);
            }
        };
    }
    /**
     * 记录聊天记录
     * @return 任务task
     */
    public static TimerTask imageDesc(String resourePath,String filePath,String loginName)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                try {
                    BaiDuImageUtils.getImageInfo(resourePath,filePath,loginName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    /**
     * 记录群组聊天记录
     * @return 任务task
     */
    public static TimerTask recordGroupChatLog(final Msg msg)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                GroupMsg groupMsg = new GroupMsg();
                groupMsg.setContent(msg.getContent());
                groupMsg.setUserId(msg.getId());
                groupMsg.setToUserId(msg.getId());
                SpringUtils.getBean(ChatLogMapper.class).insertGroupChatLog(groupMsg);
            }
        };
    }

}
