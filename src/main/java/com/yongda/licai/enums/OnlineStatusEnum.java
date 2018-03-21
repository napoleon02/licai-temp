package com.yongda.licai.enums;

/**
 * 上架.下架.待上架
 *
 * @author 苏建军
 */
public enum OnlineStatusEnum {
    UP("UP", "上架"),
    DOWN("DOWN", "下架"),
    WAIT("WAIT", "待上架");

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

    OnlineStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
