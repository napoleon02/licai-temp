package com.yongda.licai.system.dal.params;

import com.yongda.licai.system.dal.model.InvestOrderDO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: napoleon
 * @Description: 管理员后台客户订单管理页面查询参数带分页
 * @Date: 2018/1/30 10:06
 * @Modified by:
 * @Version: 1.0.0
 */
public class InvestOrderQueryParams extends InvestOrderDO implements Serializable {
    private static final long serialVersionUID = 657542671146474176L;

    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品金额
     */
    private BigDecimal productAcount;
    /**
     * 投资开始时间
     */
    private String investTimeStart;
    /**
     * 投资结束时间
     */
    private String investTimeEnd;
    /**
     * 最新操作时间开始
     */
    private String updateTimeStart;
    /**
     * 最新操作时间结束
     */
    private String updateTimeEnd;
    /**
     * 当前页
     */
    private Integer page = new Integer(1);
    /**
     * 每页记录数
     */
    private Integer rows = new Integer(10);

    /*以下参数是兼容客户前台所添加*/

    /**
     * 已持有--HOLD||已兑付--PAID
     */
    private String flag;
    /**
     * 产品认购记录标识[前台产品详情页的认购记录标识,不为空就是要查认购记录]
     */
    private String takeRecord;

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

    public BigDecimal getProductAcount() {
        return productAcount;
    }

    public void setProductAcount(BigDecimal productAcount) {
        this.productAcount = productAcount;
    }

    public String getInvestTimeStart() {
        return investTimeStart;
    }

    public void setInvestTimeStart(String investTimeStart) {
        this.investTimeStart = investTimeStart;
    }

    public String getInvestTimeEnd() {
        return investTimeEnd;
    }

    public void setInvestTimeEnd(String investTimeEnd) {
        this.investTimeEnd = investTimeEnd;
    }

    public String getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(String updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public String getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(String updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTakeRecord() {
        return takeRecord;
    }

    public void setTakeRecord(String takeRecord) {
        this.takeRecord = takeRecord;
    }
}
