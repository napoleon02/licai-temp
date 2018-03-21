package com.yongda.licai.system.dal.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "TMP_INVEST_ORDER")
public class InvestOrderDO implements Serializable {

    private static final long serialVersionUID = -7178945088349603634L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select sys_guid() from dual")
    private String id;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "ORDER_TIME")
    private Date orderTime;

    @Column(name = "PAY_TIME")
    private Date payTime;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "MEMBER_ACCOUNT")
    private String memberAccount;

    @Column(name = "MEMBER_PHONE")
    private String memberPhone;

    @Column(name = "INVEST_AMOUNT")
    private BigDecimal investAmount;

    @Column(name = "BUY_NUMBER")
    private Integer buyNumber;

    @Column(name = "BUY_FEE")
    private Integer buyFee;

    @Column(name = "EXIT_FEE")
    private Integer exitFee;

    @Column(name = "PLAN_INCOME")
    private BigDecimal planIncome;

    @Column(name = "PLAN_AMOUNT")
    private BigDecimal planAmount;

    @Column(name = "REAL_INCOME")
    private BigDecimal realIncome;

    @Column(name = "REAL_AMOUNT")
    private BigDecimal realAmount;

    @Column(name = "PLAN_INVEST_START_DATE")
    private Date planInvestStartDate;

    @Column(name = "PLAN_INVEST_END_DATE")
    private Date planInvestEndDate;

    @Column(name = "REAL_INVEST_START_DATE")
    private Date realInvestStartDate;

    @Column(name = "REAL_INVEST_END_DATE")
    private Date realInvestEndDate;

    @Column(name = "PLAN_COUNT_START_DATE")
    private Date planCountStartDate;

    @Column(name = "PLAN_COUNT_END_DATE")
    private Date planCountEndDate;

    @Column(name = "REAL_COUNT_START_DATE")
    private Date realCountStartDate;

    @Column(name = "REAL_COUNT_END_DATE")
    private Date realCountEndDate;

    @Column(name = "PLAN_TO_ACCOUNT_DATE")
    private Date planToAccountDate;

    @Column(name = "REAL_TO_ACCOUNT_DATE")
    private Date realToAccountDate;

    @Column(name = "EXIT_DATE")
    private Date exitDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "REPAYMENT_TIME")
    private Date repaymentTime;

    @Column(name = "REPAYMENT_MEMO")
    private String repaymentMemo;

    @Column(name = "IDCARD_NO")
    private String idcardNo;

    @Column(name = "CONTRACT_NO")
    private String contractNo;

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
     * @return PRODUCT_ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
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
     * @return ORDER_TIME
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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
     * @return MEMBER_NAME
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * @param memberName
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    /**
     * @return MEMBER_ACCOUNT
     */
    public String getMemberAccount() {
        return memberAccount;
    }

    /**
     * @param memberAccount
     */
    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount == null ? null : memberAccount.trim();
    }

    /**
     * @return MEMBER_PHONE
     */
    public String getMemberPhone() {
        return memberPhone;
    }

    /**
     * @param memberPhone
     */
    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone == null ? null : memberPhone.trim();
    }

    /**
     * @return INVEST_AMOUNT
     */
    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    /**
     * @param investAmount
     */
    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    /**
     * @return BUY_NUMBER
     */
    public Integer getBuyNumber() {
        return buyNumber;
    }

    /**
     * @param buyNumber
     */
    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    /**
     * @return BUY_FEE
     */
    public Integer getBuyFee() {
        return buyFee;
    }

    /**
     * @param buyFee
     */
    public void setBuyFee(Integer buyFee) {
        this.buyFee = buyFee;
    }

    /**
     * @return EXIT_FEE
     */
    public Integer getExitFee() {
        return exitFee;
    }

    /**
     * @param exitFee
     */
    public void setExitFee(Integer exitFee) {
        this.exitFee = exitFee;
    }

    /**
     * @return PLAN_INCOME
     */
    public BigDecimal getPlanIncome() {
        return planIncome;
    }

    /**
     * @param planIncome
     */
    public void setPlanIncome(BigDecimal planIncome) {
        this.planIncome = planIncome;
    }

    /**
     * @return PLAN_AMOUNT
     */
    public BigDecimal getPlanAmount() {
        return planAmount;
    }

    /**
     * @param planAmount
     */
    public void setPlanAmount(BigDecimal planAmount) {
        this.planAmount = planAmount;
    }

    /**
     * @return REAL_INCOME
     */
    public BigDecimal getRealIncome() {
        return realIncome;
    }

    /**
     * @param realIncome
     */
    public void setRealIncome(BigDecimal realIncome) {
        this.realIncome = realIncome;
    }

    /**
     * @return REAL_AMOUNT
     */
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    /**
     * @param realAmount
     */
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    /**
     * @return PLAN_INVEST_START_DATE
     */
    public Date getPlanInvestStartDate() {
        return planInvestStartDate;
    }

    /**
     * @param planInvestStartDate
     */
    public void setPlanInvestStartDate(Date planInvestStartDate) {
        this.planInvestStartDate = planInvestStartDate;
    }

    /**
     * @return PLAN_INVEST_END_DATE
     */
    public Date getPlanInvestEndDate() {
        return planInvestEndDate;
    }

    /**
     * @param planInvestEndDate
     */
    public void setPlanInvestEndDate(Date planInvestEndDate) {
        this.planInvestEndDate = planInvestEndDate;
    }

    /**
     * @return REAL_INVEST_START_DATE
     */
    public Date getRealInvestStartDate() {
        return realInvestStartDate;
    }

    /**
     * @param realInvestStartDate
     */
    public void setRealInvestStartDate(Date realInvestStartDate) {
        this.realInvestStartDate = realInvestStartDate;
    }

    /**
     * @return REAL_INVEST_END_DATE
     */
    public Date getRealInvestEndDate() {
        return realInvestEndDate;
    }

    /**
     * @param realInvestEndDate
     */
    public void setRealInvestEndDate(Date realInvestEndDate) {
        this.realInvestEndDate = realInvestEndDate;
    }

    /**
     * @return PLAN_COUNT_START_DATE
     */
    public Date getPlanCountStartDate() {
        return planCountStartDate;
    }

    /**
     * @param planCountStartDate
     */
    public void setPlanCountStartDate(Date planCountStartDate) {
        this.planCountStartDate = planCountStartDate;
    }

    /**
     * @return PLAN_COUNT_END_DATE
     */
    public Date getPlanCountEndDate() {
        return planCountEndDate;
    }

    /**
     * @param planCountEndDate
     */
    public void setPlanCountEndDate(Date planCountEndDate) {
        this.planCountEndDate = planCountEndDate;
    }

    /**
     * @return REAL_COUNT_START_DATE
     */
    public Date getRealCountStartDate() {
        return realCountStartDate;
    }

    /**
     * @param realCountStartDate
     */
    public void setRealCountStartDate(Date realCountStartDate) {
        this.realCountStartDate = realCountStartDate;
    }

    /**
     * @return REAL_COUNT_END_DATE
     */
    public Date getRealCountEndDate() {
        return realCountEndDate;
    }

    /**
     * @param realCountEndDate
     */
    public void setRealCountEndDate(Date realCountEndDate) {
        this.realCountEndDate = realCountEndDate;
    }

    /**
     * @return PLAN_TO_ACCOUNT_DATE
     */
    public Date getPlanToAccountDate() {
        return planToAccountDate;
    }

    /**
     * @param planToAccountDate
     */
    public void setPlanToAccountDate(Date planToAccountDate) {
        this.planToAccountDate = planToAccountDate;
    }

    /**
     * @return REAL_TO_ACCOUNT_DATE
     */
    public Date getRealToAccountDate() {
        return realToAccountDate;
    }

    /**
     * @param realToAccountDate
     */
    public void setRealToAccountDate(Date realToAccountDate) {
        this.realToAccountDate = realToAccountDate;
    }

    /**
     * @return EXIT_DATE
     */
    public Date getExitDate() {
        return exitDate;
    }

    /**
     * @param exitDate
     */
    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
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

    /**
     * @return IDCARD_NO
     */
    public String getIdcardNo() {
        return idcardNo;
    }

    /**
     * @param idcardNo
     */
    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo == null ? null : idcardNo.trim();
    }

    /**
     * @return CONTRACT_NO
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * @param contractNo
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }
}