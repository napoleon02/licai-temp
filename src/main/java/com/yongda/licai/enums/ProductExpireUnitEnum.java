package com.yongda.licai.enums;

/**
 * 产品期限单位
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-下午8:01
 */
public enum ProductExpireUnitEnum {

    DAY("DAY", "天", 1),
    MONTH("MONTH", "月", 30),
    YEAR("YEAR", "年", 365);

    private String code;

    private String message;

    private Integer day;

    ProductExpireUnitEnum(String code, String message, Integer day) {
        this.code = code;
        this.message = message;
        this.day = day;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
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
