package com.zh.project.monitor.druid;

import com.zh.common.utils.StringUtils;
import com.zh.framework.aspectj.lang.annotation.Log;
import com.zh.framework.aspectj.lang.enums.BusinessType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid 监控
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/data")
public class DruidController {
    private String prefix = "/druid";

    @RequiresPermissions("monitor:data:view")
    @GetMapping()
    @Log(businessType = BusinessType.SELECT,title = "数据监控")
    public String index() {
        return StringUtils.format("redirect:{}", prefix + "/index");
    }
}
