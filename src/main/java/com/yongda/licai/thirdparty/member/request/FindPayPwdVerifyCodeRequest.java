package com.yongda.licai.thirdparty.member.request;

import com.yongda.licai.thirdparty.member.BaseRequest;
import com.yongda.licai.thirdparty.member.BaseResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 找回支付密码发送短信
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午3:53
 */
public class FindPayPwdVerifyCodeRequest implements BaseRequest<BaseResponse> {

    private static final String biz = "paypwd/find/sendcode";

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
    public Map<String, Object> getParams(String publickey) {
        Map<String, Object> params = new HashMap<>();
        params.put("login_name", phone);
        return params;
    }

    @Override
    public Class<BaseResponse> getTargetClass() {
        return BaseResponse.class;
    }
}
