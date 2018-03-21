package com.yongda.licai.system.job.handle.impl;

import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.system.biz.impl.InvestOrderPaymentHandle;
import com.yongda.licai.system.dal.model.PaymentDO;
import com.yongda.licai.thirdparty.ydpay.YongDaPayClient;
import com.yongda.licai.thirdparty.ydpay.request.QueryTradeResultRequest;
import com.yongda.licai.thirdparty.ydpay.response.QueryTradeResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 查询支付结果任务
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/8-下午2:13
 */
@Component(value = "queryPaymentResultTask")
public class QueryPaymentResultTask {

    private static final Logger log = LoggerFactory.getLogger(QueryPaymentResultTask.class);

    @Resource(name = "yongDaPayClient")
    private YongDaPayClient yongDaPayClient;

    @Resource(name = "investOrderPaymentHandle")
    private InvestOrderPaymentHandle investOrderPaymentHandle;

    @Resource(name = "environment")
    private Environment environment;

    //is_success=T&_input_charset=UTF-8&result=true&isForwardCashier=false&tradeList=20180207211514427658^101151800931515769257^null^0.01^0.00^0.00^TRADE_FINISHED^20180207211613

    @Async(value = "myExecutor")
    public void query(List<PaymentDO> paymentDOS) {
        try {

            String host = "";

            String profile = environment.getProperty("profile");

            if (GlobalConst.PROFILE_PROD.equalsIgnoreCase(profile)) {
                host = environment.getProperty("yongdapay.inner.host");
            }

            if (CollUtil.isNotEmpty(paymentDOS)) {
                for (PaymentDO paymentDO : paymentDOS) {
                    QueryTradeResultRequest request = new QueryTradeResultRequest();
                    request.setOuterTradeNo(paymentDO.getOrderNo());
                    QueryTradeResultResponse response = yongDaPayClient.execute(request, host);
                    String body = response.getBody();
                    if (StrUtil.containsAny(body, "TRADE_FINISHED", "TRADE_SUCCESS")) {
                        //成功
                        Map<String, List<String>> params = HttpUtil.decodeParams(body, "UTF-8");
                        String tradeList = params.get("tradeList").get(0);
                        String[] split = tradeList.split("^");
                        String channelNo = split[1];
                        String payTime = split[7];

                        investOrderPaymentHandle.doHandle(paymentDO.getOrderNo(), channelNo, payTime);

                    }
                }
            }
        } catch (Exception e) {
            log.info("查询支付结果任务异常：", e);
        }
    }

}
