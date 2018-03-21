package com.yongda.licai.system.web.controller.admin;

import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.enums.YesOrNoEnum;
import com.yongda.licai.system.biz.AdminManagerService;
import com.yongda.licai.system.dal.model.AdminDO;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.utils.PwdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 后台登录控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/26-上午10:33
 */
@Controller
@RequestMapping(value = "/admin/login")
public class AdminLoginController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AdminLoginController.class);

    @Resource(name = "adminManagerService")
    private AdminManagerService adminManagerService;

    /**
     * 后台登录页面
     */
    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "/admin/login.html";
    }

    /**
     * 处理登录逻辑
     */
    @PostMapping(value = "/doLogin")
    @ResponseBody
    public ResponseWraper doLogin(@RequestParam String account,
                                  @RequestParam String password,
                                  @RequestParam String token) {
        try {
            HttpSession session = getSession();
            String captchaToken = (String) session.getAttribute("captchaToken");
            if (StrUtil.isBlank(account)) {
                return ResponseWraper.other("请输入帐号");
            }
            if (StrUtil.isBlank(password)) {
                return ResponseWraper.other("请输入密码");
            }
            if (StrUtil.isBlank(token)) {
                return ResponseWraper.other("请输入验证码");
            }
            if (StrUtil.isBlank(captchaToken)) {
                return ResponseWraper.other("请重新获取验证码");
            }
            if (!captchaToken.equalsIgnoreCase(token)) {
                return ResponseWraper.other("验证码输入有误");
            }
            AdminDO adminDO = adminManagerService.getByAccount(account);
            if (null == adminDO) {
                return ResponseWraper.other("用户名或密码不正确");
            }
            if (YesOrNoEnum.YES.getCode().equals(adminDO.getIsDisable())) {
                return ResponseWraper.other("用户被禁用");
            }
            String salt = adminDO.getSalt();
            password = PwdUtils.buildPwd(password, salt);
            if (!password.equalsIgnoreCase(adminDO.getPassword())) {
                return ResponseWraper.other("用户名或密码不正确");
            }
            session.setAttribute(GlobalConst.ADMIN_SESSION_KEY, adminDO);
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("管理员登录异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 退出登录
     */
    @GetMapping(value = "/logout")
    public String logout() {
        HttpSession session = getSession();
        session.invalidate();
        return "redirect:/admin/login";
    }
}
