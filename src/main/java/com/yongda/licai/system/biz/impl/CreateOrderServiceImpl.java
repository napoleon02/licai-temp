package com.yongda.licai.system.biz.impl;

import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.MapUtil;
import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.enums.*;
import com.yongda.licai.system.biz.CreateOrderService;
import com.yongda.licai.system.dal.mapper.InvestOrderDOMapper;
import com.yongda.licai.system.dal.mapper.PaymentDOMapper;
import com.yongda.licai.system.dal.mapper.ProductDOMapper;
import com.yongda.licai.system.dal.model.InvestOrderDO;
import com.yongda.licai.system.dal.model.PaymentDO;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.thirdparty.member.response.MemberInfo;
import com.yongda.licai.thirdparty.ydpay.YongDaPayClient;
import com.yongda.licai.thirdparty.ydpay.request.InstantPayRequest;
import com.yongda.licai.thirdparty.ydpay.request.TradeParamsParams;
import com.yongda.licai.thirdparty.ydpay.response.InstantPayResponse;
import com.yongda.licai.utils.TradeNoUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 创建订单业务接口实现
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-下午3:39
 */
@Service(value = "createOrderService")
public class CreateOrderServiceImpl implements CreateOrderService {

    @Resource(name = "productDOMapper")
    private ProductDOMapper productDOMapper;

    @Resource(name = "investOrderDOMapper")
    private InvestOrderDOMapper investOrderDOMapper;

    @Resource(name = "paymentDOMapper")
    private PaymentDOMapper paymentDOMapper;

    @Resource(name = "yongDaPayClient")
    private YongDaPayClient yongDaPayClient;

    @Resource(name = "environment")
    private Environment environment;

    @Transactional
    @Override
    public ResponseWraper createOrder(String productId, BigDecimal amount, MemberInfo memberInfo, String returnUrl, String notifyUrl) {
        synchronized (this) {
            Date currentDate = new Date();

            //行锁
            ProductDO productDO = productDOMapper.findByIdWithForUpdate(productId);

            if (null == productDO) {
                return ResponseWraper.newInstance("ORDER_002", "产品信息不存在");
            }

            if (!ProductStatusEnum.DO_BUY.getCode().equalsIgnoreCase(productDO.getStatus())) {
                return ResponseWraper.newInstance("ORDER_003", "产品已售罄");
            }

            BigDecimal totalAmount = productDO.getAmount();
            Integer minUnit = productDO.getMinUnit();
            BigDecimal maxAmount = productDO.getMaxAmount();
            BigDecimal minAmount = productDO.getMinAmount();
            //BigDecimal sellNumber = productDO.getSellNumber();

            if (minAmount.compareTo(amount) == 1) {
                return ResponseWraper.newInstance("ORDER_004", "投资金额小于最小投资金额");
            }

            if (maxAmount.compareTo(amount) == -1) {
                return ResponseWraper.newInstance("ORDER_005", "投资金额大于最大投资金额");
            }

            BigDecimal remainder = amount.remainder(new BigDecimal(minUnit));
            if (new BigDecimal(0).compareTo(remainder) != 0) {
                return ResponseWraper.newInstance("ORDER_006", "投资金额必须为投资单位整倍数");
            }

            if (!checkInvestAmount(productId, amount, maxAmount)) {
                return ResponseWraper.newInstance("ORDER_007", "累计投资金额大于最大投资金额");
            }

            //实时查询出已销售的额度
            BigDecimal sellAmount = investOrderDOMapper.queryAlreadyBuyAmount(productId);
            if (null == sellAmount) {
                sellAmount = new BigDecimal("0");
            }
            BigDecimal add = sellAmount.add(amount);
            //BigDecimal add = sellNumber.add(amount);

            //产品总金额小于（已售出+当前投资额）
            if (totalAmount.compareTo(add) == -1) {
                return ResponseWraper.newInstance("ORDER_008", "投资金额大于可投资金额");
            }

            int updateFlag = productDOMapper.updateSellNumber(productId, amount);
            if (updateFlag <= 0) {
                return ResponseWraper.newInstance("ORDER_008", "投资金额大于可投资金额");
            }

            //创建支付流水
            PaymentDO paymentDO = new PaymentDO();
            paymentDO.setChannelCode(PaymentChannelEnum.YONGDA_PAY.getCode());
            paymentDO.setChannelName(PaymentChannelEnum.YONGDA_PAY.getMessage());
            paymentDO.setBizCode(PaymentBizEnum.BUY_FINANCIAL_PRODUCT.getCode());
            paymentDO.setBizName(PaymentBizEnum.BUY_FINANCIAL_PRODUCT.getMessage());
            paymentDO.setOrderNo(TradeNoUtils.genTradeNo());
            paymentDO.setPayStatus(PaymentStatusEnum.WAIT.getCode());
            paymentDO.setMemberId(memberInfo.getMember_id());
            paymentDO.setProductName(productDO.getProductNo() + "_" + productDO.getName());
            paymentDO.setProductPrice(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
            paymentDO.setProductNumber(1);
            paymentDO.setTradePrice(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
            paymentDO.setCreateTime(currentDate);
            paymentDO.setUpdateTime(currentDate);

            //创建订单记录
            InvestOrderDO orderDO = new InvestOrderDO();
            orderDO.setProductId(productId);
            orderDO.setOrderNo(paymentDO.getOrderNo());
            orderDO.setOrderTime(currentDate);
            orderDO.setMemberId(memberInfo.getMember_id());
            orderDO.setMemberAccount(memberInfo.getLogin_name());
            orderDO.setMemberPhone(memberInfo.getVerify_mobile());
            orderDO.setMemberName(memberInfo.getName());
            orderDO.setInvestAmount(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
            orderDO.setStatus(OrderStatusEnum.WAITPAY.getCode());
            orderDO.setPlanInvestStartDate(currentDate);            //预计投资开始时间段
            orderDO.setPlanInvestEndDate(productDO.getPlanDate());  //预计投资结束时间段
            orderDO.setRealInvestStartDate(currentDate);            //实际投资开始时间段
            orderDO.setPlanToAccountDate(productDO.getPlanDate());  //设置预计到账时间段
            setPlanCountDate(productDO, orderDO, currentDate);      //设置预计起息时间段
            orderDO.setRealCountStartDate(orderDO.getPlanCountStartDate());//实际计息开始时间
            setPlanIncome(productDO, orderDO);                      //设置预计收益
            orderDO.setCreateTime(currentDate);
            orderDO.setUpdateTime(currentDate);
            orderDO.setIdcardNo(memberInfo.getIdcard_no());

            int payInsertFlag = paymentDOMapper.insertSelective(paymentDO);
            int orderInsertFlag = investOrderDOMapper.insertSelective(orderDO);

            if (payInsertFlag > 0 && orderInsertFlag > 0) {
                TradeParamsParams tradeParamsParams = new TradeParamsParams();
                tradeParamsParams.setOrderNo(paymentDO.getOrderNo());
                tradeParamsParams.setProductName(paymentDO.getProductName());

                //根据环境设置支付金额
                String profile = environment.getProperty("profile");
                if (GlobalConst.PROFILE_DEV.equalsIgnoreCase(profile)
                        || GlobalConst.PROFILE_TEST.equalsIgnoreCase(profile)) {
                    tradeParamsParams.setProductPrice("0.01");
                    tradeParamsParams.setTradPrice("0.01");
                } else {
                    tradeParamsParams.setProductPrice(paymentDO.getProductPrice().toString());
                    tradeParamsParams.setTradPrice(paymentDO.getTradePrice().toString());
                }

                tradeParamsParams.setProductNumber("1");
                tradeParamsParams.setSellerId(environment.getProperty("yongdapay.sellerId"));
                tradeParamsParams.setSellerIdType("1");
                tradeParamsParams.setBusinessNo(PaymentBizEnum.BUY_FINANCIAL_PRODUCT.getCode());

                //回调地址
                tradeParamsParams.setAsyncNotifyUrl(notifyUrl);

                InstantPayRequest request = new InstantPayRequest();
                request.setRequestNo(paymentDO.getOrderNo());
                request.setBuyerId(memberInfo.getLogin_name());
                request.setBuyerIdType("1");
                request.setTradeList(Arrays.asList(tradeParamsParams));

                //同步返回地址
                request.setReturnUrl(returnUrl);

                InstantPayResponse response = yongDaPayClient.pageExecute(request);
                Map<String, String> requestParams = response.getRequestParams();

                return ResponseWraper.ok().setData(MapUtil.builder()
                        .put("url", yongDaPayClient.getHost())
                        .put("body", requestParams).map());
            } else {
                throw new RuntimeException("创建理财订单异常");
            }
        }
    }

    /**
     * 设置预计收益
     *
     * @param productDO     产品信息
     * @param investOrderDO 投资订单信息
     */
    private void setPlanIncome(ProductDO productDO, InvestOrderDO investOrderDO) {
        BigDecimal apr = productDO.getApr();
        BigDecimal investAmount = investOrderDO.getInvestAmount();

        long day = DateUtil.betweenDay(investOrderDO.getPlanCountStartDate(),
                investOrderDO.getPlanCountEndDate(), true);

        //年华利率需要除以100
        apr = apr.divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);

        //利息计算公式=投资额*年华利率*投资期限/365
        BigDecimal result = investAmount.multiply(apr)
                .multiply(new BigDecimal(day))
                .divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);

        investOrderDO.setPlanIncome(result);
        investOrderDO.setPlanAmount(investAmount.add(result).setScale(2, BigDecimal.ROUND_HALF_UP));
    }


    /**
     * 设置预计计息时间段
     *
     * @param productDO     产品信息
     * @param investOrderDO 投资订单信息
     * @param currentDate   当前时间
     */
    private void setPlanCountDate(ProductDO productDO, InvestOrderDO investOrderDO, Date currentDate) {
        String unit = productDO.getUnit();
        Integer limit = productDO.getLimit();
        String startType = productDO.getStartType();

        int day = 0;
        Date startDate = currentDate;

        if (ProductExpireUnitEnum.DAY.getCode().equalsIgnoreCase(unit)) {
            day = ProductExpireUnitEnum.DAY.getDay() * limit;
        } else if (ProductExpireUnitEnum.MONTH.getCode().equalsIgnoreCase(unit)) {
            day = ProductExpireUnitEnum.MONTH.getDay() * limit;
        } else if (ProductExpireUnitEnum.YEAR.getCode().equalsIgnoreCase(unit)) {
            day = ProductExpireUnitEnum.YEAR.getDay() * limit;
        }


        if (ProductCountTypeEnum.DIRECT.getCode().equalsIgnoreCase(startType)) {
            startDate = productDO.getStartDay();
        } else if (ProductCountTypeEnum.T_1.getCode().equalsIgnoreCase(startType)) {
            startDate = DateUtil.offsetDay(currentDate, 1);
        } else if (ProductCountTypeEnum.T_2.getCode().equalsIgnoreCase(startType)) {
            startDate = DateUtil.offsetDay(currentDate, 2);
        } else if (ProductCountTypeEnum.T_3.getCode().equalsIgnoreCase(startType)) {
            startDate = DateUtil.offsetDay(currentDate, 3);
        }

        Date endDate = DateUtil.offsetDay(startDate, day);

        investOrderDO.setPlanCountStartDate(startDate);     //预计计息开始时间段
        investOrderDO.setPlanCountEndDate(endDate);         //预计计息结束时间段
    }


    /**
     * 校验投资额
     *
     * @param productId 产品ID
     * @param amount    投资额
     * @param maxAmount 最大投资额
     * @return boolean
     */
    private boolean checkInvestAmount(String productId, BigDecimal amount, BigDecimal maxAmount) {
        BigDecimal decimal = new BigDecimal(0);
        Example example = new Example(InvestOrderDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        criteria.andNotEqualTo("status", OrderStatusEnum.CLOSED.getCode());

        List<InvestOrderDO> list = investOrderDOMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(list)) {
            for (InvestOrderDO orderDO : list) {
                BigDecimal investAmount = orderDO.getInvestAmount();
                decimal.add(investAmount);
            }
        }

        BigDecimal sumAmount = decimal.add(amount);
        //最大投资额小于累计投资额
        return maxAmount.compareTo(sumAmount) != -1;
    }
}
