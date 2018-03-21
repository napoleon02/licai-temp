package com.yongda.licai.system.biz.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.enums.OrderStatusEnum;
import com.yongda.licai.system.biz.OrderManagementService;
import com.yongda.licai.system.dal.dto.InvestOrderDto;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.mapper.ProductDOMapper;
import com.yongda.licai.system.dal.mapper.SeqMapper;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.dal.params.InvestOrderQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author: napoleon
 * @Description: 管理员后台客户订单管理接口实现
 * @Date: 2018/1/30 10:14
 * @Modified by:
 * @Version: 1.0.0
 */
@Service(value = "orderManagementService")
public class OrderManagementServiceImpl implements OrderManagementService {

    private Logger logger = LoggerFactory.getLogger(OrderManagementServiceImpl.class);

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    @Resource(name = "productDOMapper")
    private ProductDOMapper productDOMapper;

    @Resource(name = "seqMapper")
    private SeqMapper seqMapper;

    /**
     * @Author:nepoleon
     * @Descriptions: 列表分页查询客户产生的订单
     * @Param:investOrderQueryParams 前台页面查询参数实体
     * @Date: 2018/1/30 10:45
     */
    @Override
    public Page<InvestOrderDto> queryAllOrderInfoByPage(InvestOrderQueryParams investOrderQueryParams) {
        Page<InvestOrderDto> result = PageHelper.startPage(investOrderQueryParams.getPage(), investOrderQueryParams.getRows()).doSelectPage(new ISelect() {
            @Override
            public void doSelect() {
                try {
                    investOrderDOMapper.queryAllOrderInfoByPage(investOrderQueryParams);
                } catch (SQLException e) {
                    logger.error("列表分页查询客户产生的订单{}", e);
                }
            }
        });
        return result;
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单ID查看该订单的交易详情
     * @Param:id 订单ID
     * @Date: 2018/1/30 14:25
     */
    @Override
    public InvestOrderDto queryOrderDetailsById(String id) {
        return investOrderDOMapper.queryOrderDetailsById(id);
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据订单ID更改该笔订单状态[支持的修改状态:退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS]
     * @Param:ids 要更新的订单IDS[支持批量]
     * @Param:flag 退款-REFUNDED,关闭-CLOSED,还款-REPAYSUCCESS
     * @Date: 2018/2/3 9:30
     */
    @Override
    public Boolean updateOrderStatus(String ids, String flag) {
        try {
            String[] orderIds = ids.split(",");
            Integer count = investOrderDOMapper.updateOrderStatus(orderIds, changeFlagToNum(flag));
            if (count == orderIds.length) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            logger.error("根据订单ID更改该笔订单状态[支持的修改状态:退款-REFUNDED,关闭-CLOSED]{}", e);
            return false;
        }

    }

    @Override
    public InvestOrderDO findByOrderNo(String orderNo) {
        Example example = new Example(InvestOrderDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo", orderNo);
        return investOrderDOMapper.selectOneByExample(example);
    }

    @Transactional
    @Override
    public Integer closeUnPaymentOrder(Integer minute) {
        //同步锁，该方法体只允许一个线程进入
        synchronized (this) {
            List<InvestOrderDO> list = investOrderDOMapper.queryUnPaymentOrder(minute);
            if (CollUtil.isNotEmpty(list)) {
                Map<String, BigDecimal> decimalMap = new HashMap<>();
                for (InvestOrderDO investOrderDO : list) {
                    String productId = investOrderDO.getProductId();
                    BigDecimal investAmount = investOrderDO.getInvestAmount();

                    if (!decimalMap.containsKey(productId)) {
                        decimalMap.put(productId, new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }

                    BigDecimal decimal = decimalMap.get(productId);
                    decimalMap.put(productId, decimal.add(investAmount).setScale(2, BigDecimal.ROUND_HALF_UP));

                    //关闭订单
                    InvestOrderDO orderDO = new InvestOrderDO();
                    orderDO.setId(investOrderDO.getId());
                    orderDO.setStatus(OrderStatusEnum.CLOSED.getCode());
                    orderDO.setUpdateTime(new Date());
                    investOrderDOMapper.updateByPrimaryKeySelective(orderDO);
                }

                //循环map逐个修改相应产品的投资金额
                for (Map.Entry<String, BigDecimal> entry : decimalMap.entrySet()) {
                    //取反就是做减法操作
                    BigDecimal decimal = entry.getValue().negate();
                    //行锁
                    ProductDO productDO = productDOMapper.findByIdWithForUpdate(entry.getKey());
                    if (null != productDO) {
                        productDOMapper.updateSellNumber(productDO.getId(), decimal);
                    }
                }
                return list.size();
            } else {
                return 0;
            }
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions:后台订单还款单起接口[支持批量还款]
     * @Param:investOrderDO 订单实体
     * @Date: 2018/2/7 16:38
     */
    @Transactional
    @Override
    public Map orderRepayForOneOrMany(InvestOrderDO investOrderDO) {
        Map map = new HashMap();
        String[] orderIds = investOrderDO.getId().split(",");
        Example example = new Example(InvestOrderDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(orderIds));
        List<InvestOrderDO> investOrderDOS = investOrderDOMapper.selectByExample(example);
        Boolean flag = true;
        for (InvestOrderDO investOrderDO1 : investOrderDOS) {
            if (OrderStatusEnum.WAITPAY.getCode().equals(investOrderDO1.getStatus())
                    || OrderStatusEnum.ALREADPAY.getCode().equals(investOrderDO1.getStatus())
                    || OrderStatusEnum.RATING.getCode().equals(investOrderDO1.getStatus())
                    || OrderStatusEnum.REPAYSUCCESS.getCode().equals(investOrderDO1.getStatus())
                    || OrderStatusEnum.CLOSED.getCode().equals(investOrderDO1.getStatus())
                    || OrderStatusEnum.REFUNDED.getCode().equals(investOrderDO1.getStatus())) {
                flag = false;
            } else {
                investOrderDO1.setRealIncome(investOrderDO1.getPlanIncome());
            }
        }
        /*合法状态状态完毕 开始执行批量更新*/
        if (flag) {
            for (InvestOrderDO order : investOrderDOS) {
                InvestOrderDO _order = new InvestOrderDO();
                _order.setId(order.getId());
                if (OrderStatusEnum.EXPIRED.getCode().equals(order.getStatus())) {
                    _order.setRealIncome(order.getPlanIncome());
                    _order.setRealAmount(order.getPlanAmount());
                }
                _order.setRepaymentTime(investOrderDO.getRepaymentTime());
                _order.setRepaymentMemo(investOrderDO.getRepaymentMemo());
                _order.setStatus(OrderStatusEnum.REPAYSUCCESS.getCode());
                _order.setUpdateTime(new Date());
                investOrderDOMapper.updateByPrimaryKeySelective(_order);
            }
            map.put("success", true);
            map.put("msg", "订单批量还款完成.");
        } else {
            map.put("success", false);
            map.put("msg", "提交的订单中有非法订单状态[不能进行还款操作]");
        }
        return map;
    }

    /**
     * @Author:nepoleon
     * @Descriptions:对未付款的订单进行关闭同时释放购买的金额
     * @Param:id 要关闭订单的ID
     * @Date: 2018/2/7 19:17
     */
    @Transactional
    @Override
    public void closeOrder(String id) {
        synchronized (this) {
            InvestOrderDO investOrderDO = new InvestOrderDO();
            investOrderDO.setId(id);
            investOrderDO.setStatus(OrderStatusEnum.WAITPAY.getCode());
            InvestOrderDO orderDO = investOrderDOMapper.selectOne(investOrderDO);
            if (null != orderDO) {
                String productId = orderDO.getProductId();
                BigDecimal investAmount = orderDO.getInvestAmount();
                //取反得到负数
                BigDecimal amount = investAmount.negate();
                //行锁
                ProductDO productDO = productDOMapper.findByIdWithForUpdate(productId);
                if (null != productDO) {
                    productDOMapper.updateSellNumber(productId, amount);
                    //修改订单状态
                    InvestOrderDO investOrderDO2 = new InvestOrderDO();
                    investOrderDO2.setStatus(OrderStatusEnum.CLOSED.getCode());
                    investOrderDO2.setId(id);
                    investOrderDO2.setUpdateTime(new Date());
                    investOrderDOMapper.updateByPrimaryKeySelective(investOrderDO2);
                }
            }
        }
    }

    /**
     * @Author:nepoleon
     * @Descriptions:对已付款但是没有开始计息的订单进行退款操作同时释放金额
     * @Param:id 要退款订单的ID
     * @Date: 2018/2/7 19:17
     */
    @Transactional
    @Override
    public void refundOrder(String id) {
        synchronized (this) {
            InvestOrderDO investOrderDO = new InvestOrderDO();
            investOrderDO.setId(id);
            investOrderDO.setStatus(OrderStatusEnum.ALREADPAY.getCode());
            InvestOrderDO orderDO = investOrderDOMapper.selectOne(investOrderDO);
            if (null != orderDO) {
                String productId = orderDO.getProductId();
                BigDecimal investAmount = orderDO.getInvestAmount();
                //取反得到负数
                BigDecimal amount = investAmount.negate();
                //行锁
                ProductDO productDO = productDOMapper.findByIdWithForUpdate(productId);
                if (null != productDO) {
                    productDOMapper.updateSellNumber(productId, amount);
                    //修改订单状态
                    InvestOrderDO investOrderDO3 = new InvestOrderDO();
                    investOrderDO3.setStatus(OrderStatusEnum.REFUNDED.getCode());
                    investOrderDO3.setId(id);
                    investOrderDO3.setUpdateTime(new Date());
                    investOrderDOMapper.updateByPrimaryKeySelective(investOrderDO3);
                }
            }
        }
    }

    @Override
    public String genContractNo() {
        String number = StrUtil.fillBefore(seqMapper.getContractNo(), '0', 6);
        String format = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        return GlobalConst.CONTRACT_NUMBER_PREFIX + format + number;
    }

    /**
     * @Author:nepoleon
     * @Descriptions: 标识转换
     * @Param:flag 标识
     * @Date: 2018/2/7 16:41
     */
    public String changeFlagToNum(String flag) {
        if ("WAITPAY".equals(flag)) {
            return flag = "1";
        } else if ("ALREADPAY".equals(flag)) {
            return flag = "2";
        } else if ("RATING".equals(flag)) {
            return flag = "3";
        } else if ("EXPIRED".equals(flag)) {
            return flag = "4";
        } else if ("REPAYING".equals(flag)) {
            return flag = "5";
        } else if ("REPAYSUCCESS".equals(flag)) {
            return flag = "6";
        } else if ("REPAYFAIL".equals(flag)) {
            return flag = "7";
        } else if ("CLOSED".equals(flag)) {
            return flag = "8";
        } else if ("REFUNDED".equals(flag)) {
            return flag = "9";
        } else {
            return "";
        }
    }


}
