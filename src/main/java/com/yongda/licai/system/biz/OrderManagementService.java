package com.yongda.licai.system.biz;

import com.github.pagehelper.Page;
import com.yongda.licai.system.dal.dto.InvestOrderDto;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.dal.params.InvestOrderQueryParams;

import java.util.Map;

/**
 * @Author: napoleon
 * @Description: 管理员后台客户订单管理接口
 * @Date: 2018/1/30 9:51
 * @Modified by:
 * @Version: 1.0.0
 */
public interface OrderManagementService {

    /**
     * @Author:nepoleon
     * @Descriptions: 列表分页查询客户产生的订单
     * @Param:null
     * @Date: 2018/1/30 10:45
     */
    Page<InvestOrderDto> queryAllOrderInfoByPage(InvestOrderQueryParams investOrderQueryParams);

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单ID查看该订单的交易详情
     * @Param:id 订单ID
     * @Date: 2018/1/30 14:25
     */
    InvestOrderDto queryOrderDetailsById(String id);

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单ID更改该笔订单状态[支持的修改状态:退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS]
     * @Param:ids 要更新的订单IDS[支持批量]
     * @Param:flag 退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS
     * @Date: 2018/2/3 9:30
     */
    Boolean updateOrderStatus(String ids, String flag);

    /**
     * 根据订单编号查询投资订单详情
     *
     * @param orderNo 订单编号
     * @return InvestOrderDO
     */
    InvestOrderDO findByOrderNo(String orderNo);

    /**
     * 关闭未支付的订单
     *
     * @param minute 分钟
     */
    Integer closeUnPaymentOrder(Integer minute);

    /**
     * @Author:nepoleon
     * @Descriptions:后台订单还款单起接口[支持批量还款]
     * @Param:investOrderDO 订单实体
     * @Date: 2018/2/7 16:38
     */
    Map orderRepayForOneOrMany(InvestOrderDO investOrderDO);

    /**
     * @Author:nepoleon
     * @Descriptions:对未付款的订单进行关闭同时释放购买的金额
     * @Param:id 要关闭订单的ID
     * @Date: 2018/2/7 19:17
     */
    void closeOrder(String id);

    /**
     * @Author:nepoleon
     * @Descriptions:对已付款但是没有开始计息的订单进行退款操作同时释放金额
     * @Param:id 要退款订单的ID
     * @Date: 2018/2/7 19:17
     */
    void refundOrder(String id);

    /**
     * 生成合同编号
     *
     * @return String
     */
    String genContractNo();
}
