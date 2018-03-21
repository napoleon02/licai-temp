package com.yongda.licai.enums;

/**
 * 支付状态枚举
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-下午5:28
 */
public enum PaymentStatusEnum {

    WAIT("WAIT", "待付款"),
    SUCCESS("SUCCESS", "支付成功"),
    FINISHED("FINISHED", "支付结束"),
    CLOSED("CLOSED", "支付关闭");

    private String code;

    private String message;

    PaymentStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

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
}
