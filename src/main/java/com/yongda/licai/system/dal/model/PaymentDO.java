package com.yongda.licai.system.dal.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "TMP_PAYMENT")
public class PaymentDO implements Serializable {

    private static final long serialVersionUID = -826130517772587954L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select sys_guid() from dual")
    private String id;

    @Column(name = "CHANNEL_CODE")
    private String channelCode;

    @Column(name = "CHANNEL_NAME")
    private String channelName;

    @Column(name = "BIZ_CODE")
    private String bizCode;

    @Column(name = "BIZ_NAME")
    private String bizName;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "PAY_STATUS")
    private String payStatus;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_PRICE")
    private BigDecimal productPrice;

    @Column(name = "PRODUCT_NUMBER")
    private Integer productNumber;

    @Column(name = "TRADE_PRICE")
    private BigDecimal tradePrice;

    @Column(name = "CHANNEL_NO")
    private String channelNo;

    @Column(name = "PAY_TIME")
    private Date payTime;

    @Column(name = "NOTIFY_TIME")
    private Date notifyTime;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "NOTIFY_PARAMS")
    private String notifyParams;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return CHANNEL_CODE
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * @param channelCode
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    /**
     * @return CHANNEL_NAME
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param channelName
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    /**
     * @return BIZ_CODE
     */
    public String getBizCode() {
        return bizCode;
    }

    /**
     * @param bizCode
     */
    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }

    /**
     * @return BIZ_NAME
     */
    public String getBizName() {
        return bizName;
    }

    /**
     * @param bizName
     */
    public void setBizName(String bizName) {
        this.bizName = bizName == null ? null : bizName.trim();
    }

    /**
     * @return ORDER_NO
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * @return PAY_STATUS
     */
    public String getPayStatus() {
        return payStatus;
    }

    /**
     * @param payStatus
     */
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    /**
     * @return MEMBER_ID
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * @return PRODUCT_NAME
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * @return PRODUCT_PRICE
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return PRODUCT_NUMBER
     */
    public Integer getProductNumber() {
        return productNumber;
    }

    /**
     * @param productNumber
     */
    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    /**
     * @return TRADE_PRICE
     */
    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    /**
     * @param tradePrice
     */
    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    /**
     * @return CHANNEL_NO
     */
    public String getChannelNo() {
        return channelNo;
    }

    /**
     * @param channelNo
     */
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo == null ? null : channelNo.trim();
    }

    /**
     * @return PAY_TIME
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * @param payTime
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * @return NOTIFY_TIME
     */
    public Date getNotifyTime() {
        return notifyTime;
    }

    /**
     * @param notifyTime
     */
    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return NOTIFY_PARAMS
     */
    public String getNotifyParams() {
        return notifyParams;
    }

    /**
     * @param notifyParams
     */
    public void setNotifyParams(String notifyParams) {
        this.notifyParams = notifyParams == null ? null : notifyParams.trim();
    }
}