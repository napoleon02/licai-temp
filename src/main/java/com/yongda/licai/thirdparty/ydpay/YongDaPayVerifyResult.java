package com.yongda.licai.thirdparty.ydpay;

import java.util.HashMap;
import java.util.Map;

/**
 * 永达支付验签结果
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/3-下午1:04
 */
public class YongDaPayVerifyResult {

    public static final String exMsg = "exMsg";

    public static final String msg = "msg";

    public static final String identityNo = "identityNo";

    private boolean success = false;

    private boolean needPostCheck = false;

    private Map<String, Object> info = new HashMap<>();

    public static String getExMsg() {
        return exMsg;
    }

    public static String getMsg() {
        return msg;
    }

    public static String getIdentityNo() {
        return identityNo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isNeedPostCheck() {
        return needPostCheck;
    }

    public void setNeedPostCheck(boolean needPostCheck) {
        this.needPostCheck = needPostCheck;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public YongDaPayVerifyResult(boolean success) {
        this.success = success;
    }

    public YongDaPayVerifyResult() {
    }

    public void addInfo(String key, Object val) {
        this.info.put(key, val);
    }

    @Override
    public String toString() {
        return "YongDaPayVerifyResult{" +
                "success=" + success +
                ", needPostCheck=" + needPostCheck +
                ", info=" + info +
                '}';
    }
}
