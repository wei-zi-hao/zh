package com.zh.project.system.chat.controller;


import com.alibaba.fastjson.JSONObject;
import com.zh.common.exception.file.FileSizeLimitExceededException;
import com.zh.common.utils.ShiroUtils;
import com.zh.common.utils.chat.MsgUtils;
import com.zh.common.utils.file.FileUploadUtils;
import com.zh.framework.config.RuoYiConfig;
import com.zh.framework.web.controller.BaseController;
import com.zh.framework.web.damain.AjaxResult;
import com.zh.framework.web.page.TableDataInfo;
import com.zh.project.monitor.online.domain.UserOnline;
import com.zh.project.monitor.online.mapper.UserOnlineMapper;
import com.zh.project.system.chat.domain.*;
import com.zh.project.system.chat.mapper.ChatLogMapper;
import com.zh.project.system.chat.mapper.ChatMapper;
import com.zh.project.system.chat.service.ChatService;
import com.zh.project.system.user.domain.User;
import com.zh.project.system.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.Session;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/chat")
public class ChatController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private String prefix = "chat";
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ChatLogMapper chatLogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOnlineMapper userOnlineMapper;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMapper chatMapper;

    // 在线聊天室
    @GetMapping("/chatRoom")
    public String chatRoom(ModelMap mmap) {
        User user = ShiroUtils.getSysUser();
        mmap.put("user", user);
        return prefix + "/chatRoom";
    }

    // 消息盒子
    @GetMapping("/msgbox")
    public String chatMsgBox(ModelMap mmap) {
        return prefix + "/msgbox";
    }

    // 聊天记录
    @GetMapping("/chatlog")
    public String chatLog(String id) {
        return prefix + "/chatlog";
    }

    // 查找好友
    @GetMapping("/find")
    public String chatFind() {
        return prefix + "/find";
    }

    /**
     * 上传图片请求
     */
    @PostMapping("/img/upload")
    @ResponseBody
    public FileResult uploadImg(MultipartFile file) throws Exception {
        try {
            // 上传路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);

            return FileResult.success(fileName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return FileResult.error("文件超过限制大小。");
        }
    }


    /**
     * 上传文件请求
     */
    @PostMapping("/file/upload")
    @ResponseBody
    public FileResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);

            return FileResult.success(fileName, file.getOriginalFilename());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e instanceof FileSizeLimitExceededException) {
                return FileResult.error("文件大小超过10M！");
            } else {
                return FileResult.error(e.getMessage());
            }
        }
    }


    /**
     * 聊天记录
     */
    @PostMapping("/selectChatlogByFriend")
    @ResponseBody
    public TableDataInfo selectChatlogByFriend(String id, String type) {
        String loginName = ShiroUtils.getLoginName();
        if (null == id) {
            throw new RuntimeException("聊天记录id错误");
        }
        List<ChatLog> chatLogs;
        startPage();
        if("group".equals(type)){
             chatLogs = chatLogMapper.selectChatLogByGroup();
        }else{
            chatLogs = chatLogMapper.selectChatLogByFriend(id, loginName);
        }
        return getDataTable(chatLogs);
    }

    /**
     * 获取未读记录
     */
    @PostMapping("/getUnReadMessage")
    @ResponseBody
    @Transactional
    public List<ChatLog> getUnReadMessageUrl() {
        System.out.println(redisTemplate.hasKey("name"));
        redisTemplate.opsForValue().set("s","aaa");
        String loginName = ShiroUtils.getLoginName();
        //查询完 修改已读状态
        List<ChatLog> chatLogs = chatLogMapper.getUnReadMessageUrl(loginName);
        chatLogMapper.updateState(loginName);
        return chatLogs;
    }

    /**
     * 获取推荐好友
     */
    @PostMapping("/getUserListByRecommen")
    @ResponseBody
    public TableDataInfo getUserListByRecommen() {
        List<User> list = userMapper.selectUserListByRecommen(ShiroUtils.getLoginName());
        return getDataTable(list);
    }

    /**
     * 修改签名
     */
    @PostMapping("/updateSign")
    @ResponseBody
    @Transactional
    public AjaxResult updateSign(String sign) {

        User user = getSysUser();
        user.setRemark(sign);
        try {
            userOnlineMapper.updateOnlineSign(sign, user.getLoginName());
            userMapper.updateUser(user);
            setSysUser(user);
            return AjaxResult.success();
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    /**
     * 获取初始化数据
     */
    @RequestMapping("/getInitResult")
    @ResponseBody
    public InitResult getInitResult() {
        return chatService.getInitResult();
    }

    /**
     * 获取群员数据
     */
    @RequestMapping("/getMembers")
    @ResponseBody
    public AjaxResult getMembers() {
        //查询上线用户的信息
        List<UserOnline> userOnlines = userOnlineMapper.selectUserOnlineList(new UserOnline());
        List<InitUser> initUserList = new LinkedList<>();
        for(int i = 0 ; i<userOnlines.size(); i++){
            InitUser initUser = new InitUser();
            initUser.setUsername(userOnlines.get(i).getLoginName());
            initUser.setSign(userOnlines.get(i).getSign());
            initUser.setAvatar(userOnlines.get(i).getAvatar());
            initUser.setId(userOnlines.get(i).getLoginName());
            initUserList.add(initUser);
        }
        GroupMemberData groupMemberData = new GroupMemberData();
        groupMemberData.setList(initUserList);
        return   AjaxResult.success(groupMemberData);
    }
    /**
     * 好友申请
     */
    @RequestMapping("/friendApply")
    @ResponseBody
    public AjaxResult friendApply(ChatApply chatApply) {

        User user = ShiroUtils.getSysUser();
        chatApply.setFromId(user.getLoginName());
        chatApply.setSign(user.getRemark());
        chatApply.setAvatar(user.getAvatar());

        int row = chatMapper.selectFirstApply(chatApply);
        if (row > 0) {
            return error("你已经发送过申请，请静候佳音！");
        }
        int row2 = chatService.friendApply(chatApply);

        //推送消息
        ConcurrentHashMap<String, Session> mapUS = WsController.getMapUS();
        if (mapUS.containsKey(chatApply.getToId())) {
            String text = JSONObject.toJSONString(MsgUtils.getBoxMsg());
            mapUS.get(chatApply.getToId()).getAsyncRemote().sendText(text);
        }
        return toAjax(row2);
    }

    /**
     * 获取消息盒子内容
     */
    @RequestMapping("/getMsgBoxList")
    @ResponseBody
    public TableDataInfo getMsgBoxList() {
        startPage();
        List<ChatApply> chatApplyList = chatService.selectMsgBoxList();
        return getDataTable(chatApplyList);
    }
    /**
     * 获取消息盒子内容
     */
    @RequestMapping("/getUnReadMsgBoxList")
    @ResponseBody
    public TableDataInfo getUnReadMsgBoxList() {
        List<ChatApply> chatApplyList = chatMapper.selectUnReadMsgBoxList(ShiroUtils.getLoginName());
        return getDataTable(chatApplyList);
    }

    /**
     * 拒绝好友申请
     */
    @RequestMapping("/refuseFriend")
    @ResponseBody
    public AjaxResult refuseFriend(String uid) {
        int row = chatService.refuseFriend(uid);
        //推送消息
        ConcurrentHashMap<String, Session> mapUS = WsController.getMapUS();
        if (mapUS.containsKey(uid)) {
            String text = JSONObject.toJSONString(MsgUtils.getBoxMsg());
            mapUS.get(uid).getAsyncRemote().sendText(text);
        }
        return toAjax(row);
    }

    /**
     * 同意好友申请
     */
    @RequestMapping("/agreeFriend")
    @ResponseBody
    public AjaxResult agreeFriend(String uid) {
        int friendRow = chatService.selectFriendByFriendId(uid);
        if (friendRow > 0) {
            return error("对方已是你的好友！");
        }

        int row = chatService.agreeFriend(uid);
        //推送消息
        ConcurrentHashMap<String, Session> mapUS = WsController.getMapUS();
        if (mapUS.containsKey(uid)) {
            String text = JSONObject.toJSONString(MsgUtils.getBoxMsg(getSysUser(),"add"));
            mapUS.get(uid).getAsyncRemote().sendText(text);
        }
        return toAjax(row);
    }


    /**
     * 删除好友
     */
    @RequestMapping("/deleteFriend")
    @ResponseBody
    public AjaxResult deleteFriend(String userId) {
        int row = chatService.deleteFriend(ShiroUtils.getLoginName(), userId);


        //推送消息
        ConcurrentHashMap<String, Session> mapUS = WsController.getMapUS();
        if (mapUS.containsKey(userId)) {
            String text = JSONObject.toJSONString(MsgUtils.getBoxMsg(getSysUser(),"remove"));
            mapUS.get(userId).getAsyncRemote().sendText(text);
        }
        return toAjax(row);
    }
}
