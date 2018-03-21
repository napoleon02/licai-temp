package com.yongda.licai.enums;

public enum ProductStatusEnum {

    BEGIN("BEGIN", "即将开始"),
    DO_BUY("DO_BUY", "立即抢购"),
    BUY_OVER("BUY_OVER", "抢购结束"),
    COUNT("COUNT", "计息中"),
    EXPIRE("EXPIRE", "到期"),
    REFUND("REFUND", "还款中"),
    REFUND_OVER("REFUND_OVER", "还款成功"),
    LOSE("LOSE", "已流标");

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

    ProductStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
