package com.yongda.licai.thirdparty.ydpay;

import java.util.Map;

/**
 * 永达支付请求
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/2-下午1:07
 */
public interface YongDaPayRequest<T extends YongDaPayResponse> {

    /**
     * 获取响应类
     *
     * @return
     */
    Class<T> getResponseClass();

    /**
     * 获取请求参数
     *
     * @return Map
     */
    Map<String, String> getParams();
}
