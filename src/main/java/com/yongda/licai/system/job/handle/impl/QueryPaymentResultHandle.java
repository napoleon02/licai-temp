package com.yongda.licai.system.job.handle.impl;

import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.date.DateTime;
import com.xiaoleilu.hutool.date.DateUtil;
import com.yongda.licai.enums.PaymentStatusEnum;
import com.yongda.licai.system.dal.mapper.PaymentDOMapper;
import com.yongda.licai.system.dal.model.PaymentDO;
import com.yongda.licai.system.job.handle.MinuteJobHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 查询支付订单结果定时任务
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/8-下午1:51
 */
@Component(value = "queryPaymentResultHandle")
public class QueryPaymentResultHandle implements MinuteJobHandle {

    private static final Logger log = LoggerFactory.getLogger(QueryPaymentResultHandle.class);

    @Resource(name = "paymentDOMapper")
    private PaymentDOMapper paymentDOMapper;

    @Resource(name = "queryPaymentResultTask")
    private QueryPaymentResultTask queryPaymentResultTask;

    @Async(value = "myExecutor")
    @Override
    public void execute(Date date) {
        try {
            DateTime dateTime = DateUtil.offsetHour(date, -1);
            Example example = new Example(PaymentDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("payStatus", PaymentStatusEnum.WAIT.getCode());
            criteria.andGreaterThanOrEqualTo("createTime", dateTime);

            List<PaymentDO> list = paymentDOMapper.selectByExample(example);
            if (CollUtil.isNotEmpty(list)) {
                List<List<PaymentDO>> split = CollUtil.split(list, 10);
                for (List<PaymentDO> paymentDOS : split) {
                    queryPaymentResultTask.query(paymentDOS);
                }
            }
        } catch (Exception e) {
            log.error("定时任务-查询支付订单结果异常：", e);
        }

    }
}
