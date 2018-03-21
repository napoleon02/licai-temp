package com.yongda.licai.thirdparty.member.request;

import com.yongda.licai.thirdparty.member.BaseRequest;
import com.yongda.licai.thirdparty.member.BaseResponse;
import com.yongda.licai.utils.LKRSASignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 重置登录密码
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午3:48
 */
public class ResetLoginPwdRequest implements BaseRequest<BaseResponse> {

    private static final String biz = "loginpwd/find/reset";

    /**
     * 帐号
     */
    private String account;

    /**
     * 验证码
     */
    private String code;

    /**
     * 新密码
     */
    private String newPwd;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    @Override
    public String getBiz() {
        return biz;
    }

    @Override
    public Map<String, Object> getParams(String publickey) {
        Map<String, Object> params = new HashMap<>();
        params.put("login_name", account);
        params.put("code", code);
        params.put("new_login_pwd", LKRSASignUtil.encode(newPwd, publickey));
        return params;
    }

    @Override
    public Class<BaseResponse> getTargetClass() {
        return BaseResponse.class;
    }
}
