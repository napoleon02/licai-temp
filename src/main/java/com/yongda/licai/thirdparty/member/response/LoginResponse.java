package com.yongda.licai.thirdparty.member.response;

import com.yongda.licai.thirdparty.member.BaseResponse;

/**
 * 登录响应
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午4:35
 */
public class LoginResponse extends BaseResponse {

    private MemberInfo data;

    public MemberInfo getData() {
        return data;
    }

    public void setData(MemberInfo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "data=" + data +
                "} " + super.toString();
    }
}

