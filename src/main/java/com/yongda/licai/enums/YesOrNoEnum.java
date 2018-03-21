package com.yongda.licai.enums;

/**
 * 是或否枚举
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/20-上午9:06
 */
public enum YesOrNoEnum {

    YES("Y", "是"),
    NO("N", "否");

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    YesOrNoEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
