package com.yongda.licai.thirdparty.member.request;

import com.yongda.licai.thirdparty.member.BaseRequest;
import com.yongda.licai.thirdparty.member.BaseResponse;
import com.yongda.licai.utils.LKRSASignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 重置支付密码
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午3:56
 */
public class ResetPayPwdRequest implements BaseRequest<BaseResponse> {

    private static final String biz = "paypwd/find/reset";

    /**
     * 短信验证码
     */
    private String code;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 支付密码
     */
    private String payPwd;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    @Override
    public String getBiz() {
        return biz;
    }

    @Override
    public Map<String, Object> getParams(String publickey) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("idcard_no", idcard);
        params.put("pay_pwd", LKRSASignUtil.encode(payPwd, publickey));
        return params;
    }

    @Override
    public Class<BaseResponse> getTargetClass() {
        return BaseResponse.class;
    }
}
