package com.yongda.licai.system.job.handle.impl;

import com.yongda.licai.system.biz.OrderManagementService;
import com.yongda.licai.system.job.handle.MinuteJobHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 关闭未支付订单处理器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/3-下午3:05
 */
@Component(value = "closeUnPaymentOrderHandle")
public class CloseUnPaymentOrderHandle implements MinuteJobHandle {

    private static final Logger log = LoggerFactory.getLogger(CloseUnPaymentOrderHandle.class);

    @Resource(name = "orderManagementService")
    private OrderManagementService orderManagementService;

    @Async(value = "myExecutor")
    @Override
    public void execute(Date date) {
        try {
            orderManagementService.closeUnPaymentOrder(3);
        } catch (Exception e) {
            log.error("关闭3分钟内未支付订单异常：", e);
        }
    }
}
