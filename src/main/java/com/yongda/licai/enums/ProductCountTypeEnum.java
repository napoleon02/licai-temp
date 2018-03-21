package com.yongda.licai.enums;

/**
 * 产品起息日类型
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-下午8:28
 */
public enum ProductCountTypeEnum {

    DIRECT("DIRECT", "指定日期"),
    T_1("T+1", "T+1日"),
    T_2("T+2", "T+2日"),
    T_3("T+3", "T+3日");

    private String code;

    private String message;

    ProductCountTypeEnum(String code, String message) {
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
