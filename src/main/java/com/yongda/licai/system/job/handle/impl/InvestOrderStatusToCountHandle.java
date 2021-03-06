package com.yongda.licai.system.job.handle.impl;

import com.yongda.licai.enums.OrderStatusEnum;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.job.handle.MinuteJobHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 投资订单状态变更为计息中状态处理器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/7-下午4:10
 */
@Component(value = "investOrderStatusToCountHandle")
public class InvestOrderStatusToCountHandle implements MinuteJobHandle {

    private static final Logger log = LoggerFactory.getLogger(InvestOrderStatusToCountHandle.class);

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    @Async(value = "myExecutor")
    @Override
    public void execute(Date date) {
        try {
            InvestOrderDO investOrderDO = new InvestOrderDO();
            investOrderDO.setStatus(OrderStatusEnum.RATING.getCode());
            investOrderDO.setUpdateTime(new Date());

            Example example = new Example(InvestOrderDO.class);
            Example.Criteria criteria = example.createCriteria();

            criteria.andEqualTo("status", OrderStatusEnum.ALREADPAY.getCode());
            criteria.andLessThanOrEqualTo("realCountStartDate", date);
            investOrderDOMapper.updateByExampleSelective(investOrderDO, example);
        } catch (Exception e) {
            log.error("将投资订单状态变更为计息中状态异常：", e);
        }

    }
}
