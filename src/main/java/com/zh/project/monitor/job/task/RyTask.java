package com.zh.project.monitor.job.task;

import com.zh.common.utils.SpringUtils;
import com.zh.common.utils.StringUtils;
import com.zh.project.system.chat.mapper.ChatLogMapper;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    public void deleteChatlog() {
        SpringUtils.getBean(ChatLogMapper.class).deleteChatlog();
        System.out.println("删除一个月以前的聊天记录");
    }
}
