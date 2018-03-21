package com.yongda.licai.thirdparty.ydpay.request;

/**
 * 基础请求
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/2-下午1:29
 */
public class BaseRequest {

    /**
     * 同步返回页面地址
     */
    private String returnUrl;

    /**
     * 备注
     */
    private String memo;

    public String getReturnUrl() {
        return returnUrl;
    }

    public BaseRequest setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public BaseRequest setMemo(String memo) {
        this.memo = memo;
        return this;
    }
}
