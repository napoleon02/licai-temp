package com.yongda.licai.utils;

import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.RandomUtil;

import java.util.Date;

/**
 * 交易流水号工具类
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-下午5:38
 */
public class TradeNoUtils {

    /**
     * 创建交易流水号
     *
     * @return
     */
    public static synchronized String genTradeNo() {
        return DateUtil
                .format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + RandomUtil.randomNumbers(3);
    }

}
