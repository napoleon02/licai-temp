package com.yongda.licai.thirdparty.ydpay.request;

/**
 * 交易参数
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/1-上午10:47
 */
public class TradeParamsParams {

    /**
     * 商户网站唯一订单号 1
     */
    public String orderNo;

    /**
     * 商品名称 2
     */
    private String productName;

    /**
     * 商品单价 3
     */
    private String productPrice;

    /**
     * 商品数量 4
     */
    private String productNumber;

    /**
     * 交易金额 5
     */
    private String tradPrice;

    /**
     * 交易金额分润帐号集 6
     */
    private String splitProfitAccounts;

    /**
     * 卖家ID 7
     */
    private String sellerId;

    /**
     * 卖家标识ID类型 8
     */
    private String sellerIdType = "1";

    /**
     * 业务号 9
     */
    private String businessNo;

    /**
     * 商品描述 10
     */
    private String productDesc;

    /**
     * 商品展示URL 11
     */
    private String productUrl;

    /**
     * 金额保留字段一 12
     */
    private String price1;

    /**
     * 金额保留字段二 13
     */
    private String price2;

    /**
     * 文字保留字段
     */
    private String text;

    /**
     * 订单提交时间 14
     */
    private String submitTime;

    /**
     * 异步通知URL 15
     */
    private String asyncNotifyUrl;

    /**
     * 店铺名称 16
     */
    private String storeName;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSplitProfitAccounts() {
        return splitProfitAccounts;
    }

    public void setSplitProfitAccounts(String splitProfitAccounts) {
        this.splitProfitAccounts = splitProfitAccounts;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerIdType() {
        return sellerIdType;
    }

    public void setSellerIdType(String sellerIdType) {
        this.sellerIdType = sellerIdType;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getAsyncNotifyUrl() {
        return asyncNotifyUrl;
    }

    public void setAsyncNotifyUrl(String asyncNotifyUrl) {
        this.asyncNotifyUrl = asyncNotifyUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTradPrice() {
        return tradPrice;
    }

    public void setTradPrice(String tradPrice) {
        this.tradPrice = tradPrice;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TradeParamsParams{" +
                "orderNo='" + orderNo + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productNumber='" + productNumber + '\'' +
                ", tradPrice='" + tradPrice + '\'' +
                ", splitProfitAccounts='" + splitProfitAccounts + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", sellerIdType='" + sellerIdType + '\'' +
                ", businessNo='" + businessNo + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", price1='" + price1 + '\'' +
                ", price2='" + price2 + '\'' +
                ", text='" + text + '\'' +
                ", submitTime='" + submitTime + '\'' +
                ", asyncNotifyUrl='" + asyncNotifyUrl + '\'' +
                ", storeName='" + storeName + '\'' +
                '}';
    }
}
