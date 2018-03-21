package com.yongda.licai.system.dal.mapper;

import com.yongda.licai.system.dal.dto.InvestOrderDto;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.dal.params.InvestOrderQueryParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: napoleon
 * @Description: 管理员后台客户订单管理MAPPER
 * @Date: 2018/1/30 10:14
 * @Modified by:
 * @Version: 1.0.0
 */
@Repository(value = "investOrderDOMapper")
public interface InvestOrderDOMapper extends Mapper<InvestOrderDO> {

    /**
     * @Author:nepoleon
     * @Descriptions: 列表分页查询客户产生的订单数据
     * @Param:investOrderQueryParams 前台页面查询参数实体
     * @Date: 2018/1/30 11:04
     */
    List<InvestOrderDto> queryAllOrderInfoByPage(InvestOrderQueryParams investOrderQueryParams) throws SQLException;

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单ID查看该订单的交易详情
     * @Param:id 订单ID
     * @Date: 2018/1/30 15:29
     */
    InvestOrderDto queryOrderDetailsById(String id);

    /**
     * @Author:nepoleon
     * @Descriptions:查询该产品下已经被购买的金额数
     * @Param:productId 产品ID
     * @Date: 2018/2/1 17:44
     */
    BigDecimal queryAlreadyBuyAmount(String productId);

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单ID更改该笔订单状态[支持的修改状态:退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS]
     * @Param:ids 要更新的订单IDS[支持批量]
     * @Param:flag 退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS
     * @Date: 2018/2/3 9:30
     */
    Integer updateOrderStatus(@Param("array") String[] ids, @Param("flag") String flag) throws SQLException;

    /**
     * 查询未支付的订单记录
     *
     * @param minute 分钟
     * @return List
     */
    List<InvestOrderDO> queryUnPaymentOrder(@Param("minute") Integer minute);

    /**
     * @Author:nepoleon
     * @Descriptions:根据当前登录会员查询其符合计算累计收益和持有本息的订单列表
     * @Param:memberId 当前登录会员ID
     * @Return: 当前登录会员ID的符合条件的订单列表
     * @Date: 2018/2/6 10:31
     */
    List<InvestOrderDto> queryRealIncomeAndAllIncomeSum(String memberId) throws SQLException;
}