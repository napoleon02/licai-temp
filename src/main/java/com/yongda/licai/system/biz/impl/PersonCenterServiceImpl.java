package com.yongda.licai.system.biz.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoleilu.hutool.date.DateUtil;
import com.yongda.licai.enums.OrderStatusEnum;
import com.yongda.licai.enums.ProductExpireUnitEnum;
import com.yongda.licai.system.biz.PersonCenterService;
import com.yongda.licai.system.dal.dto.InvestOrderDto;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.params.InvestOrderQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: napoleon
 * @Description: 客户端个人中心SERVICEIMPL
 * @Date: 2018/1/31 15:17
 * @Modified by:
 * @Version: 1.0.0
 */
@Service("personCenterService")
public class PersonCenterServiceImpl implements PersonCenterService {

    private Logger logger = LoggerFactory.getLogger(PersonCenterServiceImpl.class);

    private static final String REALINCOMESUM = "realIncomeSum";
    private static final String CYBXSUM = "cybxSum";

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    /**
     * @Author:nepoleon
     * @Descriptions: 前台个人中心查询该会员下已持有||已兑付的标的
     * @Param:investOrderQueryParams 标的查询参数 memberId-当前登录会员||flag(已持有--HOLD||已兑付--PAID)
     * @Date: 2018/1/31 15:31
     */
    @Override
    public Page<InvestOrderDto> queryOrderInfoByMemberIdAndConditions(InvestOrderQueryParams investOrderQueryParams) {
        Page<InvestOrderDto> result = PageHelper.startPage(investOrderQueryParams.getPage(), investOrderQueryParams.getRows()).doSelectPage(new ISelect() {
            @Override
            public void doSelect() {
                try {
                    List<InvestOrderDto> investOrderDtos = investOrderDOMapper.queryAllOrderInfoByPage(investOrderQueryParams);
                    calculateIncomeFromOrderList(investOrderDtos);
                } catch (SQLException e) {
                    logger.error("前台个人中心查询该会员下已持有||已兑付的标的{}", e);
                }
            }
        });
        return result;
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据当前登录会员查询其累计收益[计息中的订单收益根据当前时间动态变化]和持有本息.
     * @Param:memeberId 当前登录会员ID
     * @Date: 2018/2/6 10:21
     */
    @Override
    public Map queryRealIncomeAndAllIncomeSum(String memeberId) {
        try {
            List<InvestOrderDto> investOrderDOS = investOrderDOMapper.queryRealIncomeAndAllIncomeSum(memeberId);
            investOrderDOS = calculateIncomeFromOrderList(investOrderDOS);
            return calculateRealIncomeSumAndCybxSum(investOrderDOS);
        } catch (SQLException e) {
            logger.error("根据当前登录会员查询其累计收益[计息中的订单收益根据当前时间动态变化]和持有本息{}", e);
            return null;
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 动态计算利息方法封装
     * @Param:orderList 符合进行计算的客户订单
     * @Return:累计收益和持有本息MAP
     * @Date: 2018/2/6 10:21
     */
    public List<InvestOrderDto> calculateIncomeFromOrderList(List<InvestOrderDto> orderList) {

        /*如果该会员投资产品不等于空 动态计算计息中的订单目前到起息日的利息*/
        for (InvestOrderDto dto : orderList) {

            if (OrderStatusEnum.RATING.getCode().equals(dto.getStatus())) {
                /*计息中的订单状态*/

                Date currentDate = new Date();
                Date realCountStartDate = dto.getRealCountStartDate() == null ? dto.getPlanCountStartDate() : dto.getRealCountStartDate();
                /*计算该笔订单目前计息总天数*/
                Long dayCount = DateUtil.betweenDay(currentDate, realCountStartDate, true);
                /*该笔订单的投资金额*/
                BigDecimal investAmount = dto.getInvestAmount();
                /*该笔订单投资产品的期限*/
                Integer limit = calculateDayCount(dto);
                BigDecimal apr = dto.getApr().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
                BigDecimal currentIncome = investAmount.multiply(apr).multiply(new BigDecimal(dayCount)).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
                /*订单最大收益*/
                BigDecimal allIncome = investAmount.multiply(apr).multiply(new BigDecimal(limit)).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal sumIncomeFromMinAmount = getSumIncomeFromMinAmount(currentIncome, allIncome);
                dto.setRealIncome(sumIncomeFromMinAmount);
            } else if (OrderStatusEnum.ALREADPAY.getCode().equals(dto.getStatus())) {
                /*已付款的订单状态*/
                dto.setRealIncome(new BigDecimal(0));
            } else {
                /*其余的订单状态*/
                if (dto.getRealIncome() == null) {
                    dto.setRealIncome(dto.getPlanIncome() == null ? new BigDecimal(0) : dto.getPlanIncome());
                }
            }
        }
        return orderList;
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单详情计算该订单对应产品的投资期限
     * @Param:dto 订单实体
     * @Date: 2018/2/6 13:23
     */
    public Integer calculateDayCount(InvestOrderDto dto) {
        if (ProductExpireUnitEnum.YEAR.getCode().equals(dto.getUnit())) {
            return dto.getLimit() * ProductExpireUnitEnum.YEAR.getDay();
        } else if (ProductExpireUnitEnum.MONTH.getCode().equals(dto.getUnit())) {
            return dto.getLimit() * ProductExpireUnitEnum.MONTH.getDay();
        } else if (ProductExpireUnitEnum.DAY.getCode().equals(dto.getUnit())) {
            return dto.getLimit();
        } else {
            return 0;
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions: BIGDECIMAL比较大小
     * @Param:de1 值1
     * @Param:de2 值2
     * @Date: 2018/2/6 13:43
     */
    public BigDecimal getSumIncomeFromMinAmount(BigDecimal de1, BigDecimal de2) {
        if (de1.compareTo(de2) == 1) {
            return de2;
        } else {
            return de1;
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 个人中心计算累计收益和持有本息
     * @Param:orderList 个人订单list
     * @Date: 2018/2/6 17:31
     */
    public Map calculateRealIncomeSumAndCybxSum(List<InvestOrderDto> orderList) {
        Map map = new HashMap();
        BigDecimal realIncomeSum = new BigDecimal("0");
        BigDecimal cybxSum = new BigDecimal("0");
        for (InvestOrderDto dto : orderList) {
            BigDecimal realIncome = dto.getRealIncome() == null ? new BigDecimal(0) : dto.getRealIncome();
            realIncomeSum = realIncomeSum.add(realIncome);
            cybxSum = cybxSum.add(dto.getInvestAmount()).add(realIncome);
        }
        map.put(REALINCOMESUM, realIncomeSum);
        map.put(CYBXSUM, cybxSum);
        return map;
    }

}
