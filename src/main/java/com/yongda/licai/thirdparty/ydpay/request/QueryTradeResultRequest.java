package com.yongda.licai.thirdparty.ydpay.request;

import com.yongda.licai.thirdparty.ydpay.YongDaPayRequest;
import com.yongda.licai.thirdparty.ydpay.response.QueryTradeResultResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * 查询支付交易结果请求
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/8-上午10:52
 */
public class QueryTradeResultRequest extends BaseRequest implements YongDaPayRequest<QueryTradeResultResponse> {

    /**
     * 查询支付结果网关接口
     */
    private String service = "query_trade";

    /**
     * 版本
     */
    private String version = "1.0";

    /**
     * 商户支付请求号
     */
    private String outerTradeNo;

    /**
     * 永达平台交易号
     */
    private String innerTradeNo;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    @Override
    public Class<QueryTradeResultResponse> getResponseClass() {
        return QueryTradeResultResponse.class;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> map = new TreeMap<>();
        map.put("service", getService());
        map.put("version", getVersion());
        map.put("return_url", getReturnUrl());
        map.put("memo", getMemo());
        map.put("outer_trade_no", getOuterTradeNo());
        map.put("inner_trade_no", getInnerTradeNo());
        return map;
    }
}
