package com.yongda.licai.system.dal.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "TMP_PRODUCT")
public class ProductDO implements Serializable {

    private static final long serialVersionUID = -658989111751059565L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select sys_guid() from dual")
    private String id;

    @Column(name = "PRODUCT_NO")
    private String productNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "LIMIT")
    private Integer limit;

    @Column(name = "APR")
    private BigDecimal apr;

    @Column(name = "MIN_UNIT")
    private Integer minUnit;

    @Column(name = "MAX_AMOUNT")
    private BigDecimal maxAmount;

    @Column(name = "MIN_AMOUNT")
    private BigDecimal minAmount;

    @Column(name = "START_TYPE")
    private String startType;

    @Column(name = "START_DAY")
    private Date startDay;

    @Column(name = "INCOME_MODE")
    private String incomeMode;

    @Column(name = "BID_BEGIN_DATE")
    private Date bidBeginDate;

    @Column(name = "BID_END_DATE")
    private Date bidEndDate;

    @Column(name = "COUNT_OVER_DATE")
    private Date countOverDate;

    @Column(name = "PLAN_DATE")
    private Date planDate;

    @Column(name = "IS_EARLY_END")
    private String isEarlyEnd;

    @Column(name = "BUY_FEE_DESC")
    private String buyFeeDesc;

    @Column(name = "EXIT_FEE_DESC")
    private String exitFeeDesc;

    @Column(name = "INCOME_DESC")
    private String incomeDesc;

    @Column(name = "CAPITAL_DESC")
    private String capitalDesc;

    @Column(name = "PRODUCT_DESC")
    private String productDesc;

    @Column(name = "ASSETS_CHANNEL")
    private String assetsChannel;

    @Column(name = "ASSETS_INFO")
    private String assetsInfo;

    @Column(name = "REPAY_SOURCE")
    private String repaySource;

    @Column(name = "RISK_MEASURES")
    private String riskMeasures;

    @Column(name = "PROTOCOL_NAME")
    private String protocolName;

    @Column(name = "PROTOCOL_FILE")
    private String protocolFile;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_PERSON_ID")
    private String createPersonId;

    @Column(name = "CREATE_PERSON_NAME")
    private String createPersonName;

    @Column(name = "ONLINE_STATUS")
    private String onlineStatus;

    @Column(name = "SELL_NUMBER")
    private BigDecimal sellNumber;

    @Column(name = "UPDATE_PERSON_ID")
    private String updatePersonId;

    @Column(name = "UPDATE_PERSON_NAME")
    private String updatePersonName;

    @Column(name = "REPAYMENT_TIME")
    private Date repaymentTime;

    @Column(name = "REPAYMENT_MEMO")
    private String repaymentMemo;

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
     * @return PRODUCT_NO
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * @param productNo
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return AMOUNT
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return UNIT
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * @return LIMIT
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return APR
     */
    public BigDecimal getApr() {
        return apr;
    }

    /**
     * @param apr
     */
    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    /**
     * @return MIN_UNIT
     */
    public Integer getMinUnit() {
        return minUnit;
    }

    /**
     * @param minUnit
     */
    public void setMinUnit(Integer minUnit) {
        this.minUnit = minUnit;
    }

    /**
     * @return MAX_AMOUNT
     */
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    /**
     * @param maxAmount
     */
    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    /**
     * @return MIN_AMOUNT
     */
    public BigDecimal getMinAmount() {
        return minAmount;
    }

    /**
     * @param minAmount
     */
    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    /**
     * @return START_TYPE
     */
    public String getStartType() {
        return startType;
    }

    /**
     * @param startType
     */
    public void setStartType(String startType) {
        this.startType = startType == null ? null : startType.trim();
    }

    /**
     * @return START_DAY
     */
    public Date getStartDay() {
        return startDay;
    }

    /**
     * @param startDay
     */
    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    /**
     * @return INCOME_MODE
     */
    public String getIncomeMode() {
        return incomeMode;
    }

    /**
     * @param incomeMode
     */
    public void setIncomeMode(String incomeMode) {
        this.incomeMode = incomeMode == null ? null : incomeMode.trim();
    }

    /**
     * @return BID_BEGIN_DATE
     */
    public Date getBidBeginDate() {
        return bidBeginDate;
    }

    /**
     * @param bidBeginDate
     */
    public void setBidBeginDate(Date bidBeginDate) {
        this.bidBeginDate = bidBeginDate;
    }

    /**
     * @return BID_END_DATE
     */
    public Date getBidEndDate() {
        return bidEndDate;
    }

    /**
     * @param bidEndDate
     */
    public void setBidEndDate(Date bidEndDate) {
        this.bidEndDate = bidEndDate;
    }

    /**
     * @return COUNT_OVER_DATE
     */
    public Date getCountOverDate() {
        return countOverDate;
    }

    /**
     * @param countOverDate
     */
    public void setCountOverDate(Date countOverDate) {
        this.countOverDate = countOverDate;
    }

    /**
     * @return PLAN_DATE
     */
    public Date getPlanDate() {
        return planDate;
    }

    /**
     * @param planDate
     */
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    /**
     * @return IS_EARLY_END
     */
    public String getIsEarlyEnd() {
        return isEarlyEnd;
    }

    /**
     * @param isEarlyEnd
     */
    public void setIsEarlyEnd(String isEarlyEnd) {
        this.isEarlyEnd = isEarlyEnd == null ? null : isEarlyEnd.trim();
    }

    /**
     * @return BUY_FEE_DESC
     */
    public String getBuyFeeDesc() {
        return buyFeeDesc;
    }

    /**
     * @param buyFeeDesc
     */
    public void setBuyFeeDesc(String buyFeeDesc) {
        this.buyFeeDesc = buyFeeDesc == null ? null : buyFeeDesc.trim();
    }

    /**
     * @return EXIT_FEE_DESC
     */
    public String getExitFeeDesc() {
        return exitFeeDesc;
    }

    /**
     * @param exitFeeDesc
     */
    public void setExitFeeDesc(String exitFeeDesc) {
        this.exitFeeDesc = exitFeeDesc == null ? null : exitFeeDesc.trim();
    }

    /**
     * @return INCOME_DESC
     */
    public String getIncomeDesc() {
        return incomeDesc;
    }

    /**
     * @param incomeDesc
     */
    public void setIncomeDesc(String incomeDesc) {
        this.incomeDesc = incomeDesc == null ? null : incomeDesc.trim();
    }

    /**
     * @return CAPITAL_DESC
     */
    public String getCapitalDesc() {
        return capitalDesc;
    }

    /**
     * @param capitalDesc
     */
    public void setCapitalDesc(String capitalDesc) {
        this.capitalDesc = capitalDesc == null ? null : capitalDesc.trim();
    }

    /**
     * @return PRODUCT_DESC
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * @param productDesc
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc == null ? null : productDesc.trim();
    }

    /**
     * @return ASSETS_CHANNEL
     */
    public String getAssetsChannel() {
        return assetsChannel;
    }

    /**
     * @param assetsChannel
     */
    public void setAssetsChannel(String assetsChannel) {
        this.assetsChannel = assetsChannel == null ? null : assetsChannel.trim();
    }

    /**
     * @return ASSETS_INFO
     */
    public String getAssetsInfo() {
        return assetsInfo;
    }

    /**
     * @param assetsInfo
     */
    public void setAssetsInfo(String assetsInfo) {
        this.assetsInfo = assetsInfo == null ? null : assetsInfo.trim();
    }

    /**
     * @return REPAY_SOURCE
     */
    public String getRepaySource() {
        return repaySource;
    }

    /**
     * @param repaySource
     */
    public void setRepaySource(String repaySource) {
        this.repaySource = repaySource == null ? null : repaySource.trim();
    }

    /**
     * @return RISK_MEASURES
     */
    public String getRiskMeasures() {
        return riskMeasures;
    }

    /**
     * @param riskMeasures
     */
    public void setRiskMeasures(String riskMeasures) {
        this.riskMeasures = riskMeasures == null ? null : riskMeasures.trim();
    }

    /**
     * @return PROTOCOL_NAME
     */
    public String getProtocolName() {
        return protocolName;
    }

    /**
     * @param protocolName
     */
    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName == null ? null : protocolName.trim();
    }

    /**
     * @return PROTOCOL_FILE
     */
    public String getProtocolFile() {
        return protocolFile;
    }

    /**
     * @param protocolFile
     */
    public void setProtocolFile(String protocolFile) {
        this.protocolFile = protocolFile == null ? null : protocolFile.trim();
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
     * @return STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return CREATE_PERSON_ID
     */
    public String getCreatePersonId() {
        return createPersonId;
    }

    /**
     * @param createPersonId
     */
    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId == null ? null : createPersonId.trim();
    }

    /**
     * @return CREATE_PERSON_NAME
     */
    public String getCreatePersonName() {
        return createPersonName;
    }

    /**
     * @param createPersonName
     */
    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName == null ? null : createPersonName.trim();
    }

    /**
     * @return ONLINE_STATUS
     */
    public String getOnlineStatus() {
        return onlineStatus;
    }

    /**
     * @param onlineStatus
     */
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus == null ? null : onlineStatus.trim();
    }

    /**
     * @return SELL_NUMBER
     */
    public BigDecimal getSellNumber() {
        return sellNumber;
    }

    /**
     * @param sellNumber
     */
    public void setSellNumber(BigDecimal sellNumber) {
        this.sellNumber = sellNumber;
    }

    /**
     * @return UPDATE_PERSON_ID
     */
    public String getUpdatePersonId() {
        return updatePersonId;
    }

    /**
     * @param updatePersonId
     */
    public void setUpdatePersonId(String updatePersonId) {
        this.updatePersonId = updatePersonId == null ? null : updatePersonId.trim();
    }

    /**
     * @return UPDATE_PERSON_NAME
     */
    public String getUpdatePersonName() {
        return updatePersonName;
    }

    /**
     * @param updatePersonName
     */
    public void setUpdatePersonName(String updatePersonName) {
        this.updatePersonName = updatePersonName == null ? null : updatePersonName.trim();
    }

    /**
     * @return REPAYMENT_TIME
     */
    public Date getRepaymentTime() {
        return repaymentTime;
    }

    /**
     * @param repaymentTime
     */
    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    /**
     * @return REPAYMENT_MEMO
     */
    public String getRepaymentMemo() {
        return repaymentMemo;
    }

    /**
     * @param repaymentMemo
     */
    public void setRepaymentMemo(String repaymentMemo) {
        this.repaymentMemo = repaymentMemo == null ? null : repaymentMemo.trim();
    }
}