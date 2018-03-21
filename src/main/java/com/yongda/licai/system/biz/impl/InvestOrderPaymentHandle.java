package com.yongda.licai.system.biz.impl;

import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.yongda.licai.enums.OrderStatusEnum;
import com.yongda.licai.enums.PaymentStatusEnum;
import com.yongda.licai.system.biz.OrderManagementService;
import com.yongda.licai.system.biz.PaymentService;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.mapper.PaymentDOMapper;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.dal.model.PaymentDO;
import com.yongda.licai.thirdparty.ydpay.enums.InstantNotifyStatusEnum;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 投资订单支付处理器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/6-下午3:56
 */
@Component(value = "investOrderPaymentHandle")
public class InvestOrderPaymentHandle {

    @Resource(name = "paymentDOMapper")
    private PaymentDOMapper paymentDOMapper;

    @Resource(name = "paymentService")
    private PaymentService paymentService;

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    @Resource(name = "orderManagementService")
    private OrderManagementService orderManagementService;

    /**
     * 处理逻辑
     *
     * @param params 回调参数
     */
    @Transactional
    public void doHandle(Map<String, String> params) {
        String tradeStatus = params.get("trade_status");
        String outerTradeNo = params.get("outer_trade_no");
        String innerTradeNo = params.get("inner_trade_no");
        String notifyTime = params.get("notify_time");
        String gmtPayment = params.get("gmt_payment");

        //交易成功
        if (InstantNotifyStatusEnum.TRADE_SUCCESS.getCode().equalsIgnoreCase(tradeStatus)
                || InstantNotifyStatusEnum.TRADE_FINISHED.getCode().equalsIgnoreCase(tradeStatus)) {
            PaymentDO paymentDO = paymentService.findByOrderNo(outerTradeNo);

            if (null != paymentDO) {
                String payStatus = paymentDO.getPayStatus();
                //未支付状态
                if (PaymentStatusEnum.WAIT.getCode().equalsIgnoreCase(payStatus)) {
                    PaymentDO _payment = new PaymentDO();
                    _payment.setId(paymentDO.getId());
                    _payment.setPayStatus(PaymentStatusEnum.SUCCESS.getCode());
                    _payment.setChannelNo(innerTradeNo);
                    _payment.setNotifyTime(DateUtil.parse(notifyTime));
                    _payment.setPayTime(DateUtil.parse(gmtPayment));
                    _payment.setNotifyParams(JSONUtil.toJsonStr(params));
                    _payment.setUpdateTime(new Date());

                    paymentDOMapper.updateByPrimaryKeySelective(_payment);

                    InvestOrderDO investOrderDO = orderManagementService.findByOrderNo(outerTradeNo);

                    if (null != investOrderDO) {
                        if (OrderStatusEnum.WAITPAY.getCode().equalsIgnoreCase(investOrderDO.getStatus())
                                || OrderStatusEnum.CLOSED.getCode().equalsIgnoreCase(investOrderDO.getStatus())) {
                            InvestOrderDO _investOrderDO = new InvestOrderDO();
                            _investOrderDO.setId(investOrderDO.getId());
                            _investOrderDO.setStatus(OrderStatusEnum.ALREADPAY.getCode());
                            _investOrderDO.setPayTime(DateUtil.parse(gmtPayment));
                            _investOrderDO.setUpdateTime(new Date());
                            _investOrderDO.setContractNo(orderManagementService.genContractNo());
                            investOrderDOMapper.updateByPrimaryKeySelective(_investOrderDO);
                        }
                    }

                }

            }

        }

    }

    /**
     * 处理器
     *
     * @param orderNo   订单号
     * @param channelNo 渠道订单号
     * @param payTime   支付时间
     */
    @Transactional
    public void doHandle(String orderNo, String channelNo, String payTime) {
        PaymentDO paymentDO = paymentService.findByOrderNo(orderNo);
        if (null != paymentDO) {
            String payStatus = paymentDO.getPayStatus();
            //未支付状态
            if (PaymentStatusEnum.WAIT.getCode().equalsIgnoreCase(payStatus)) {
                PaymentDO _payment = new PaymentDO();
                _payment.setId(paymentDO.getId());
                _payment.setPayStatus(PaymentStatusEnum.SUCCESS.getCode());
                _payment.setChannelNo(channelNo);
                _payment.setPayTime(DateUtil.parse(payTime));
                _payment.setUpdateTime(new Date());

                paymentDOMapper.updateByPrimaryKeySelective(_payment);

                InvestOrderDO investOrderDO = orderManagementService.findByOrderNo(orderNo);

                if (null != investOrderDO) {
                    if (OrderStatusEnum.WAITPAY.getCode().equalsIgnoreCase(investOrderDO.getStatus())
                            || OrderStatusEnum.CLOSED.getCode().equalsIgnoreCase(investOrderDO.getStatus())) {
                        InvestOrderDO _investOrderDO = new InvestOrderDO();
                        _investOrderDO.setId(investOrderDO.getId());
                        _investOrderDO.setStatus(OrderStatusEnum.ALREADPAY.getCode());
                        _investOrderDO.setPayTime(DateUtil.parse(payTime));
                        _investOrderDO.setUpdateTime(new Date());
                        investOrderDOMapper.updateByPrimaryKeySelective(_investOrderDO);
                    }
                }
            }
        }
    }

}
