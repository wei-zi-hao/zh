package com.zh.project.monitor.server.controller;

import com.zh.framework.aspectj.lang.annotation.Log;
import com.zh.framework.aspectj.lang.enums.BusinessType;
import com.zh.project.monitor.server.domain.Server;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 服务器监控
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController  {
    private String prefix = "monitor/server";


    @Log(title = "服务监控",businessType = BusinessType.SELECT)
    @RequiresPermissions("monitor:server:view")
    @GetMapping()
    public String server(ModelMap mmap) throws Exception {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        System.out.println(server);
        return prefix + "/server";
    }
}
