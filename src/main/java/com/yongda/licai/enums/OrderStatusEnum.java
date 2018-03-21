package com.yongda.licai.enums;

/**
 * @Author: napoleon
 * @Description:
 * @Date: 2018/1/30 10:53
 * @Modified by:
 * @Version: 1.0.0
 */
public enum OrderStatusEnum {

    WAITPAY("1", "待付款"),

    ALREADPAY("2", "已付款"),

    RATING("3", "计息中"),

    EXPIRED("4", "已到期"),

    REPAYING("5", "还款中"),

    REPAYSUCCESS("6", "还款成功"),

    REPAYFAIL("7", "还款失败"),

    CLOSED("8", "已关闭"),

    REFUNDED("9", "已退款");

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    OrderStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
