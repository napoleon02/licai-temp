package com.yongda.licai.thirdparty.ydpay.response;

import com.yongda.licai.thirdparty.ydpay.YongDaPayResponse;

/**
 * 查询交易结果响应
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/8-上午11:02
 */
public class QueryTradeResultResponse extends YongDaPayResponse {

    private static final long serialVersionUID = -2495376347911662510L;

    private String result;

    private String isForwardCashier;

    private String outerTradeNo;

    private String innerTradeNo;

    private String payStatus;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIsForwardCashier() {
        return isForwardCashier;
    }

    public void setIsForwardCashier(String isForwardCashier) {
        this.isForwardCashier = isForwardCashier;
    }

    public String getOuterTradeNo() {
        return outerTradeNo;
    }

    public void setOuterTradeNo(String outerTradeNo) {
        this.outerTradeNo = outerTradeNo;
    }

    public String getInnerTradeNo() {
        return innerTradeNo;
    }

    public void setInnerTradeNo(String innerTradeNo) {
        this.innerTradeNo = innerTradeNo;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
