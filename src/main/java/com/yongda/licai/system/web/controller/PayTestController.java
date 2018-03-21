package com.yongda.licai.system.web.controller;

import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
import com.yongda.licai.constant.GlobalConst;
import com.yongda.licai.system.web.response.ResponseWraper;
import com.yongda.licai.thirdparty.ydpay.YongDaPayClient;
import com.yongda.licai.thirdparty.ydpay.YongDaPayVerifyResult;
import com.yongda.licai.thirdparty.ydpay.request.InstantPayRequest;
import com.yongda.licai.thirdparty.ydpay.request.QueryTradeResultRequest;
import com.yongda.licai.thirdparty.ydpay.request.TradeParamsParams;
import com.yongda.licai.thirdparty.ydpay.response.QueryTradeResultResponse;
import com.yongda.licai.utils.WebUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 支付测试控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/2-下午2:15
 */
@Controller
@RequestMapping(value = "/test/pay")
public class PayTestController extends BaseController {

    @Resource(name = "yongDaPayClient")
    private YongDaPayClient yongDaPayClient;

    @Resource(name = "environment")
    private Environment environment;

    /**
     * 即时到账支付页面
     *
     * @return
     */
    @GetMapping(value = "/instant")
    public String index(ModelMap modelMap) {
        modelMap.put("orderNo", DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + RandomUtil.randomNumbers(3));
        return "/test/instant.html";
    }

    @GetMapping(value = "/doPay")
    @ResponseBody
    public String doPay(@RequestParam String orderNo) {
        TradeParamsParams tradeParamsParams = new TradeParamsParams();
        tradeParamsParams.setOrderNo(orderNo);
        tradeParamsParams.setProductName("理财标TEST");
        tradeParamsParams.setProductPrice("0.01");
        tradeParamsParams.setProductNumber("1");
        tradeParamsParams.setTradPrice("0.01");
        tradeParamsParams.setSellerId(yongDaPayClient.getSellerId());
        tradeParamsParams.setSellerIdType("1");
        tradeParamsParams.setBusinessNo("LICAI");
        tradeParamsParams.setAsyncNotifyUrl(WebUtils.getHostUrl() + "/test/pay/callback");

        InstantPayRequest request = new InstantPayRequest();
        request.setRequestNo(orderNo);
        request.setBuyerId("18627868269");//13867478835
        request.setBuyerIdType("1");
        request.setReturnUrl(WebUtils.getHostUrl() + "/test/pay/return");
        request.setTradeList(Arrays.asList(tradeParamsParams));

        String body = yongDaPayClient.pageExecute(request).getBody();
        System.out.println(body);
        return body;
    }

    /**
     * 回调通知
     */
    @PostMapping(value = "/callback")
    public String callBack(@RequestParam Map<String, String> map) {
        try {
            System.out.println("回调通知：" + JSONUtil.toJsonStr(map));
            YongDaPayVerifyResult result = yongDaPayClient.checkSign(map);
            if (result.isSuccess()) {
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = "/query")
    @ResponseBody
    public ResponseWraper queryOrder(@RequestParam String orderNo) {
        String host = "";
        String profile = environment.getProperty("profile");

        if (GlobalConst.PROFILE_PROD.equalsIgnoreCase(profile)) {
            host = environment.getProperty("yongdapay.inner.host");
        }

        QueryTradeResultRequest queryTradeResultRequest = new QueryTradeResultRequest();
        queryTradeResultRequest.setOuterTradeNo(orderNo);
        QueryTradeResultResponse response = yongDaPayClient.execute(queryTradeResultRequest, host);
        return ResponseWraper.ok().setData(response);
    }

    /**
     * 回调通知
     */
    @GetMapping(value = "/return")
    @ResponseBody
    public String returnUrl() {
        return "同步回调页面";
    }
}
