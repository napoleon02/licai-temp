package com.yongda.licai.thirdparty.ydpay;

import java.io.Serializable;
import java.util.Map;

/**
 * 永达支付响应
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/2-下午1:10
 */
public abstract class YongDaPayResponse implements Serializable {

    private static final long serialVersionUID = -8929558616950630816L;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 字符集
     */
    private String charSet;

    /**
     * 错误返回码
     */
    private String errorCode;

    /**
     * 错误返回消息
     */
    private String errorMessage;

    /**
     * 备注
     */
    private String memo;

    private String body;

    private Map<String, String> requestParams;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
