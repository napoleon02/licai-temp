package com.yongda.licai.system.dal.dto;

import com.yongda.licai.system.dal.model.ProductDO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: napoleon
 * @Description:
 * @Date: 2018/2/1 17:51
 * @Modified by:
 * @Version: 1.0.0
 */
public class ProductDto extends ProductDO implements Serializable {

    private static final long serialVersionUID = 9120004825998678892L;

    /**
     * 该产品剩余的可购买金额
     */
    private BigDecimal leaveAmount;

    public BigDecimal getLeaveAmount() {
        return leaveAmount;
    }

    public void setLeaveAmount(BigDecimal leaveAmount) {
        this.leaveAmount = leaveAmount;
    }
}
