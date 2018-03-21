package com.yongda.licai.thirdparty.ydpay.request;

import com.xiaoleilu.hutool.json.JSONUtil;
import com.yongda.licai.thirdparty.ydpay.YongDaPayRequest;
import com.yongda.licai.thirdparty.ydpay.YongDaPayUtil;
import com.yongda.licai.thirdparty.ydpay.response.InstantPayResponse;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 即时到账参数
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/1-上午10:07
 */
public class InstantPayRequest extends BaseRequest implements YongDaPayRequest<InstantPayResponse> {

    /**
     * 即时到账交易网关接口1.1
     */
    private String service = "create_instant_pay";

    /**
     * 版本
     */
    private String version = "1.1";

    /**
     * 商户网站请求号
     */
    private String requestNo;

    /**
     * 交易列表
     */
    private List<TradeParamsParams> tradeList;

    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 买家ID
     */
    private String buyerId;

    /**
     * 买家ID类型
     */
    private String buyerIdType = "1";

    /**
     * 买家IP地址
     */
    private String buyerIp;

    /**
     * 支付方式
     */
    private String payethod;

    /**
     * 是否转收银台标识
     */
    private String goashier;

    /**
     * 风险控制参数
     */
    private RiskParamsParams risktem;

    public String getService() {
        return service;
    }

    public InstantPayRequest setService(String service) {
        this.service = service;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public InstantPayRequest setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public InstantPayRequest setRequestNo(String requestNo) {
        this.requestNo = requestNo;
        return this;
    }

    public List<TradeParamsParams> getTradeList() {
        return tradeList;
    }

    public InstantPayRequest setTradeList(List<TradeParamsParams> tradeList) {
        this.tradeList = tradeList;
        return this;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public InstantPayRequest setOperatorId(String operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public InstantPayRequest setBuyerId(String buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public String getBuyerIdType() {
        return buyerIdType;
    }

    public InstantPayRequest setBuyerIdType(String buyerIdType) {
        this.buyerIdType = buyerIdType;
        return this;
    }

    public String getBuyerIp() {
        return buyerIp;
    }

    public InstantPayRequest setBuyerIp(String buyerIp) {
        this.buyerIp = buyerIp;
        return this;
    }

    public String getPayethod() {
        return payethod;
    }

    public InstantPayRequest setPayethod(String payethod) {
        this.payethod = payethod;
        return this;
    }

    public String getGoashier() {
        return goashier;
    }

    public InstantPayRequest setGoashier(String goashier) {
        this.goashier = goashier;
        return this;
    }

    public RiskParamsParams getRisktem() {
        return risktem;
    }

    public InstantPayRequest setRisktem(RiskParamsParams risktem) {
        this.risktem = risktem;
        return this;
    }

    @Override
    public Class<InstantPayResponse> getResponseClass() {
        return InstantPayResponse.class;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> map = new TreeMap<>();
        map.put("service", getService());
        map.put("version", getVersion());
        map.put("return_url", getReturnUrl());
        map.put("memo", getMemo());
        map.put("request_no", getRequestNo());
        map.put("trade_list", YongDaPayUtil.tradeListToStr(getTradeList()));
        map.put("operator_id", getOperatorId());
        map.put("buyer_id", getBuyerId());
        map.put("buyer_id_type", getBuyerIdType());
        map.put("buyer_ip", getBuyerIp());
        map.put("pay_method", getPayethod());
        map.put("go_cashier", getGoashier());
        map.put("risk_item", null == getRisktem() ? null : JSONUtil.toJsonStr(risktem));
        return map;
    }


}
