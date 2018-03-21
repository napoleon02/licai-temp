package com.yongda.licai.enums;

/**
 * 支付渠道枚举
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-下午5:15
 */
public enum PaymentChannelEnum {

    YONGDA_PAY("YONGDA_PAY", "永达支付");

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    PaymentChannelEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
