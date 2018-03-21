package com.yongda.licai.system.web.response;

/**
 * 响应枚举
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/13-下午5:05
 */
public enum ResponseEnum {

    SUCCESS("200", "成功"),
    FAIL("400", "失败"),
    ERROR("500", "异常"),

    UNLOGIN("402", "用户未登录"),
    LOGIN_LOCK("403", "登录帐号冻结"),
    LOGIN_WHITE("404", "登录帐号不在白名单中"),
    USER_ACCOUNT_NOT_EXIST("405", "帐号不存在"),
    ERR_SIGNATURE("401", "验签失败");

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public ResponseEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseEnum setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
