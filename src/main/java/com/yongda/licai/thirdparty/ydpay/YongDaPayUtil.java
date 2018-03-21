package com.yongda.licai.thirdparty.ydpay;

import com.xiaoleilu.hutool.collection.CollUtil;
import com.yongda.licai.thirdparty.ydpay.request.TradeParamsParams;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 支付工具类
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/1-下午2:52
 */
public class YongDaPayUtil {

    /**
     * 将交易信息列表集合转换成字符串形式
     *
     * @param params 交易信息集合
     * @return String
     */
    public static String tradeListToStr(List<TradeParamsParams> params) {
        if (CollUtil.isEmpty(params)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (TradeParamsParams param : params) {
            String str = tradeToStr(param);
            if (sb.length() > 0) {
                sb.append("$").append(str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 将交易信息转换成字符串形式
     *
     * @param params 交易信息
     * @return String
     */
    public static String tradeToStr(TradeParamsParams params) {
        if (null == params) {
            return null;
        }
        Field[] fields = params.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            String value;
            field.setAccessible(true);
            try {
                Object obj = field.get(params);
                if (null == obj) {
                    value = "";
                } else {
                    value = String.valueOf(obj);
                }
            } catch (IllegalAccessException e) {
                value = "~";
            }

            if (sb.length() > 0) {
                sb.append("~").append(value);
            } else {
                sb.append(value);
            }

        }
        return sb.toString();
    }

    public static String buildForm(String baseUrl, Map<String, String> parameters) {
        java.lang.StringBuffer sb = new StringBuffer();
        sb.append("<form name=\"punchout_form\" method=\"post\" action=\"");
        sb.append(baseUrl);
        sb.append("\">\n");
        sb.append(buildHiddenFields(parameters));

        sb.append("<input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n");
        sb.append("</form>\n");
        sb.append("<script>document.forms[0].submit();</script>");
        java.lang.String form = sb.toString();
        return form;
    }

    private static String buildHiddenFields(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return "";
        }
        java.lang.StringBuffer sb = new StringBuffer();
        Set<String> keys = parameters.keySet();
        for (String key : keys) {
            String value = parameters.get(key);
            // 除去参数中的空值
            if (key == null || value == null) {
                continue;
            }
            sb.append(buildHiddenField(key, value));
        }
        java.lang.String result = sb.toString();
        return result;
    }

    private static String buildHiddenField(String key, String value) {
        java.lang.StringBuffer sb = new StringBuffer();
        sb.append("<input type=\"hidden\" name=\"");
        sb.append(key);

        sb.append("\" value=\"");
        //转义双引号
        String a = value.replace("\"", "&quot;");
        sb.append(a).append("\">\n");
        return sb.toString();
    }
}
