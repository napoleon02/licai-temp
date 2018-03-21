package com.yongda.licai.thirdparty.member.request;

import com.yongda.licai.thirdparty.member.BaseRequest;
import com.yongda.licai.thirdparty.member.response.LoginResponse;
import com.yongda.licai.utils.LKRSASignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午3:36
 */
public class LoginRequest implements BaseRequest<LoginResponse> {

    private static final String biz = "dclogin";

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getBiz() {
        return biz;
    }

    @Override
    public Map<String, Object> getParams(String publicKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("login_name", this.account);
        params.put("login_pwd", LKRSASignUtil.encode(this.password, publicKey));
        return params;
    }

    @Override
    public Class<LoginResponse> getTargetClass() {
        return LoginResponse.class;
    }
}
