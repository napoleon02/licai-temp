package com.yongda.licai.system.biz;

import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.thirdparty.member.response.MemberInfo;

import java.math.BigDecimal;

/**
 * 创建订单业务接口
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-下午3:37
 */
public interface CreateOrderService {

    /**
     * 创建订单
     *
     * @param productId  产品ID
     * @param amount     金额
     * @param memberInfo 会员信息
     * @param returnUrl  返回地址
     * @param notifyUrl  通知地址
     * @return ResponseWraper
     */
    ResponseWraper createOrder(String productId, BigDecimal amount, MemberInfo memberInfo, String returnUrl, String notifyUrl);

}
