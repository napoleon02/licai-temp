package com.yongda.licai.thirdparty.member.request;

import com.yongda.licai.thirdparty.member.BaseRequest;
import com.yongda.licai.thirdparty.member.BaseResponse;
import com.yongda.licai.utils.LKRSASignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午4:14
 */
public class SendMobileSMSRequest implements BaseRequest<BaseResponse> {

    private static final String biz = "send/dcsms";

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 会员ID
     */
    private String memberId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
        params.put("phoneNum", phone);
        params.put("content", content);
        params.put("memberid", LKRSASignUtil.encode(memberId, publickey));
        return params;
    }

    @Override
    public Class<BaseResponse> getTargetClass() {
        return BaseResponse.class;
    }
}
