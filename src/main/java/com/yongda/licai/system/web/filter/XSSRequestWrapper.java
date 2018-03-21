package com.yongda.licai.system.web.filter;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * xss攻击请求处理
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/26-上午9:46
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = replace(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return replace(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return replace(value);
    }

    private static final String replace(String targetStr) {
        return HtmlUtils.htmlEscape(targetStr);
    }
}
