package com.yongda.licai.thirdparty.member.request;

import com.yongda.licai.thirdparty.member.BaseRequest;
import com.yongda.licai.thirdparty.member.BaseResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册帐号发送短信
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午2:56
 */
public class RegisterAccountVerifyCodeRequest implements BaseRequest<BaseResponse> {

    private static final String biz = "sendMobileVerifyCode";

    /**
     * 手机号码（登录帐号）
     */
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getBiz() {
        return biz;
    }

    @Override
    public Map<String, Object> getParams(String publicKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("login_name", phone);
        return params;
    }

    @Override
    public Class<BaseResponse> getTargetClass() {
        return BaseResponse.class;
    }
}
