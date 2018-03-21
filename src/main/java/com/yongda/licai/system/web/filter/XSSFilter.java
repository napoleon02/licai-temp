package com.yongda.licai.system.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * xss攻击过滤器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/26-上午9:55
 */
@Order(1)
@WebFilter(urlPatterns = "/*", filterName = "xssFilter")
public class XSSFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(XSSFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("XSS过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }

    @Override
    public void destroy() {
        log.info("XSS过滤器销毁");
    }
}
