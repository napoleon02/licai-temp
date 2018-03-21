package com.yongda.licai.system.web.controller.home;

import com.yongda.licai.annotation.LoginRequire;
import com.yongda.licai.system.biz.CreateOrderService;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.thirdparty.member.response.MemberInfo;
import com.yongda.licai.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 订单控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/3-下午1:56
 */
@Controller
@RequestMapping(value = "/home/order")
@Api(tags = "5.订单接口", description = "5.订单接口")
public class HomeOrderController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(HomeOrderController.class);

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "createOrderService")
    private CreateOrderService createOrderService;

    /**
     * 创建订单
     */
    @PostMapping(value = "/createOrder")
    @ResponseBody
    @LoginRequire
    @ApiOperation(value = "创建订单", httpMethod = "POST"
            , notes = "错误码：<br>" +
            "ORDER_001->订单重复提交<br>" +
            "ORDER_002->产品信息不存在<br>" +
            "ORDER_003->产品已售罄<br>" +
            "ORDER_004->投资金额小于最小投资金额<br>" +
            "ORDER_005->投资金额大于最大投资金额<br>" +
            "ORDER_006->投资金额必须为投资单位整倍数<br>" +
            "ORDER_007->累计投资金额大于最大投资金额<br>" +
            "ORDER_008->投资金额大于可投资金额<br>")
    public ResponseWraper createOrder(@ApiParam(value = "产品ID", required = true)
                                      @RequestParam String productId,
                                      @ApiParam(value = "金额", required = true)
                                      @RequestParam BigDecimal amount,
                                      @ApiParam(value = "返回地址", required = true)
                                      @RequestParam String returnUrl) {

        try {
            String token = getTokenByHome();
            MemberInfo memberInfo = getMemberInfoByHome();
            if (!checkResubmit(token)) {
                return ResponseWraper.newInstance("ORDER_001", "订单重复提交");
            }

            String notifyUrl = WebUtils.getHostUrl() + "/common/pay/callback";

            return createOrderService
                    .createOrder(productId, amount, memberInfo, returnUrl, notifyUrl);
        } catch (Exception e) {
            log.error("创建订单异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 检测重复提交
     *
     * @param token token
     * @return boolean
     */
    private boolean checkResubmit(String token) {
        String key = "CHECK_RESUBMIT";
        if (redisTemplate.hasKey(token + key)) {
            return false;
        } else {
            redisTemplate.opsForValue().set(token + key, token, 5, TimeUnit.SECONDS);
            return true;
        }
    }

}
