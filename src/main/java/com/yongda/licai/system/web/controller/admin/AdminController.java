package com.yongda.licai.system.web.controller.admin;

import com.yongda.licai.system.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理首页控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/26-上午10:26
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    /**
     * 后台首页
     */
    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "admin/index.html";
    }

    /**
     * 后台控制台页面
     */
    @GetMapping(value = "/console")
    public String console() {
        return "/admin/console.html";
    }
}
