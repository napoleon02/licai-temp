package com.yongda.licai.system.web.controller.home;

import com.yongda.licai.annotation.LoginRequire;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseEnum;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.thirdparty.member.BaseResponse;
import com.yongda.licai.thirdparty.member.MemberClient;
import com.yongda.licai.thirdparty.member.request.FindLoginPwdVerifyCodeRequest;
import com.yongda.licai.thirdparty.member.request.ResetLoginPwdRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 前台系统密码相关控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/31-下午2:26
 */
@Controller
@RequestMapping(value = "/home/pwd")
@Api(tags = "2.密码相关接口", description = "2.密码相关接口")
public class HomePwdController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(HomePwdController.class);

    @Resource(name = "createMemberClient")
    private MemberClient memberClient;

    /**
     * 发送重置登录密码短信
     */
    @PostMapping(value = "/sendRestLoginPwdSMS")
    @ResponseBody
    @ApiOperation(value = "发送重置登录密码短信", httpMethod = "POST")
    @LoginRequire(ignore = true)
    public ResponseWraper sendRestLoginPwdSMS(@ApiParam(value = "手机号码", required = true)
                                              @RequestParam String phone) {
        try {
            FindLoginPwdVerifyCodeRequest request = new FindLoginPwdVerifyCodeRequest();
            request.setPhone(phone);
            BaseResponse response = memberClient.execute(request);
            if (!response.getSuccess()) {
                String code = response.getCode();
                if ("USER_ACCOUNT_NOT_EXIST".equalsIgnoreCase(code)) {
                    return ResponseWraper.newInstance(ResponseEnum.USER_ACCOUNT_NOT_EXIST);
                } else {
                    return ResponseWraper.other(response.getMessage());
                }
            }
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("前台发送重置登录密码短信异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 重置登录密码
     */
    @PostMapping(value = "/resetLoginPwd")
    @ResponseBody
    @ApiOperation(value = "重置登录密码", httpMethod = "POST")
    @LoginRequire(ignore = true)
    public ResponseWraper resetLoginPwd(@ApiParam(value = "手机号码", required = true)
                                        @RequestParam String phone,
                                        @ApiParam(value = "短信验证码", required = true)
                                        @RequestParam String code,
                                        @ApiParam(value = "新密码", required = true)
                                        @RequestParam String newPwd) {
        try {
            ResetLoginPwdRequest request = new ResetLoginPwdRequest();
            request.setAccount(phone);
            request.setCode(code);
            request.setNewPwd(newPwd);
            BaseResponse response = memberClient.execute(request);
            if (!response.getSuccess()) {
                return ResponseWraper.other(response.getMessage());
            }
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("前台重置登录密码异常：", e);
            return ResponseWraper.error();
        }
    }
}
