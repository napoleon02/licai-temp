package com.yongda.licai.thirdparty.member.request;

import com.yongda.licai.thirdparty.member.BaseRequest;
import com.yongda.licai.thirdparty.member.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 免密登录请求
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/27-下午2:23
 */
public class DcmmLoginRequest implements BaseRequest<LoginResponse> {

    private static final String biz = "dcmmlogin";

    /**
     * 会员ID
     */
    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String getBiz() {
        return biz;
    }

    @Override
    public Map<String, Object> getParams(String publickey) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", this.memberId);
        return params;
    }

    @Override
    public Class<LoginResponse> getTargetClass() {
        return LoginResponse.class;
    }
}
