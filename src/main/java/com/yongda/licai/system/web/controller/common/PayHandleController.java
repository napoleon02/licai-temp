package com.yongda.licai.system.web.controller.common;

import com.xiaoleilu.hutool.json.JSONUtil;
import com.yongda.licai.system.biz.impl.InvestOrderPaymentHandle;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.thirdparty.ydpay.YongDaPayClient;
import com.yongda.licai.thirdparty.ydpay.YongDaPayVerifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 支付处理控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/6-下午2:24
 */
@Controller
@RequestMapping(value = "/common/pay")
public class PayHandleController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(PayHandleController.class);

    /**
     * 交易通知
     */
    private static final String TRADE_STATUS_SYNC = "trade_status_sync";

    /**
     * 退款通知
     */
    private static final String REFUND_STATUS_SYNC = "refund_status_sync";

    @Resource(name = "yongDaPayClient")
    private YongDaPayClient yongDaPayClient;

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "investOrderPaymentHandle")
    private InvestOrderPaymentHandle investOrderPaymentHandle;

    /**
     * 支付回调
     */
    @PostMapping(value = "/callback")
    @ResponseBody
    public String callback(@RequestParam Map<String, String> map) {
        try {
            log.info("支付回调参数：{}", JSONUtil.toJsonStr(map));
            //目前只有永达支付
            YongDaPayVerifyResult result = yongDaPayClient.checkSign(map);
            if (result.isSuccess()) {
                String outerTradeNo = map.get("outer_trade_no");
                String notifytype = map.get("notify_type");

                String key = "CHECK_RENOTIFY";
                if (redisTemplate.hasKey(outerTradeNo + notifytype + key)) {
                    return "next";
                } else {
                    redisTemplate.opsForValue().set(outerTradeNo + notifytype + key, "NOTIFY", 5, TimeUnit.SECONDS);

                    if (TRADE_STATUS_SYNC.equalsIgnoreCase(notifytype)) {

                        investOrderPaymentHandle.doHandle(map);

                    } else {
                        // TODO 退款待实现
                    }

                    return "success";
                }
            } else {
                log.warn("验签失败：{}", JSONUtil.toJsonStr(result));
                return "fail";
            }
        } catch (Exception e) {
            log.error("支付回调异常：", e);
            return "error";
        }
    }

}
