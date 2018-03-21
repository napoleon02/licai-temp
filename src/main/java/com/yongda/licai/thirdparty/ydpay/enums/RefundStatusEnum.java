package com.yongda.licai.thirdparty.ydpay.enums;

/**
 * 退款状态枚举
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/3-上午9:53
 */
public enum RefundStatusEnum {

    REFUND_SUCCESS("REFUND_SUCCESS", "退款成功"),
    REFUND_FAIL("REFUND_FAIL", "退款失败");

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

    RefundStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
