package com.yongda.licai.system.biz;

import com.yongda.licai.system.dal.model.PaymentDO;

/**
 * 支付业务接口
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/6-下午4:12
 */
public interface PaymentService {

    /**
     * 根据订单号查询支付信息
     *
     * @param orderNo 订单编号
     * @return PaymentDO
     */
    PaymentDO findByOrderNo(String orderNo);

}
