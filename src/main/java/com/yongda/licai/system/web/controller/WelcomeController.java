package com.yongda.licai.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 欢迎页面控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/8-上午11:16
 */
@Controller
public class WelcomeController extends BaseController {

    /**
     * 首页
     */
    @GetMapping(value = "/")
    public String index() {
        return "forward:index.html";
    }

}
