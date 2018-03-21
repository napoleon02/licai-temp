package com.yongda.licai.enums;

/**
 * 支付业务枚举
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-下午5:09
 */
public enum PaymentBizEnum {

    BUY_FINANCIAL_PRODUCT("BUY_FINANCIAL_PRODUCT", "购买理财产品");

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

    PaymentBizEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
