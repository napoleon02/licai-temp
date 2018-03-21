package com.yongda.licai.system.dal.dto;

import com.yongda.licai.system.dal.model.InvestOrderDO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: napoleon
 * @Description: 客户订单管理DTO
 * @Date: 2018/1/30 15:11
 * @Modified by:
 * @Version: 1.0.0
 */
public class InvestOrderDto extends InvestOrderDO implements Serializable {


    private static final long serialVersionUID = 6001039518470178501L;

    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品期限
     */
    private Integer limit;
    /**
     * 产品期限单位
     */
    private String unit;
    /**
     * 年化利率
     */
    private BigDecimal apr;
    /**
     * 收益支付方式
     */
    private String incomeMode;
    /**
     * 预期收益合计
     */
    private BigDecimal planIncomeSum;
    /**
     * 持有本息合计
     */
    private BigDecimal cybxSum;
    /**
     * 相关协议文件名
     */
    private String protocolName;
    /**
     * 协议文件路径
     */
    private String protocolFile;
    /**
     * 起息日
     */
    private Date startDay;
    /**
     * 起息日类型(direct指定|T+1|T+2|T+3)
     */
    private String startType;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public String getIncomeMode() {
        return incomeMode;
    }

    public void setIncomeMode(String incomeMode) {
        this.incomeMode = incomeMode;
    }

    public BigDecimal getPlanIncomeSum() {
        return planIncomeSum;
    }

    public void setPlanIncomeSum(BigDecimal planIncomeSum) {
        this.planIncomeSum = planIncomeSum;
    }

    public BigDecimal getCybxSum() {
        return cybxSum;
    }

    public void setCybxSum(BigDecimal cybxSum) {
        this.cybxSum = cybxSum;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolFile() {
        return protocolFile;
    }

    public void setProtocolFile(String protocolFile) {
        this.protocolFile = protocolFile;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
