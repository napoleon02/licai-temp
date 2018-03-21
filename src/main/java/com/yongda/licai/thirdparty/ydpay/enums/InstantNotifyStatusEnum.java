package com.yongda.licai.thirdparty.ydpay.enums;

/**
 * 即时到账交易状态通知枚举
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/3-上午9:49
 */
public enum InstantNotifyStatusEnum {

    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "交易创建，等待买家付款"),
    TRADE_SUCCESS("TRADE_SUCCESS", "交易成功"),
    TRADE_FINISHED("TRADE_FINISHED", "交易结束"),
    TRADE_CLOSED("TRADE_CLOSED", "交易关闭");

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    InstantNotifyStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
