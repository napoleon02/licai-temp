package com.yongda.licai.system.biz;

import com.github.pagehelper.Page;
import com.yongda.licai.system.dal.dto.InvestOrderDto;
import com.yongda.licai.system.dal.params.InvestOrderQueryParams;

import java.util.Map;

/**
 * @Author: napoleon
 * @Description: 客户端个人中心SERVICE
 * @Date: 2018/1/31 15:14
 * @Modified by:
 * @Version: 1.0.0
 */
public interface PersonCenterService {

    /**
     * @Author:nepoleon
     * @Descriptions: 前台个人中心查询该会员下已持有||已兑付的标的
     * @Param:investOrderQueryParams 标的查询参数 memberId-当前登录会员||flag(已持有--HOLD||已兑付--PAID)
     * @Date: 2018/1/31 15:31
     */
    Page<InvestOrderDto> queryOrderInfoByMemberIdAndConditions(InvestOrderQueryParams investOrderQueryParams);

    /**
     * @Author:nepoleon
     * @Descriptions: 根据当前登录会员查询其累计收益[计息中的订单收益根据当前时间动态变化]和持有本息.
     * @Param:memeberId 当前登录会员ID
     * @Date: 2018/2/6 10:21
     */
    Map queryRealIncomeAndAllIncomeSum(String memeberId);
}
