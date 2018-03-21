package com.yongda.licai.config;

import com.xiaoleilu.hutool.date.DateTime;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Date;

/**
 * Web相关配置
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/2-下午4:22
 */
@Configuration
public class WebConfig {

    /**
     * 将前台传递的日期字符串格式化成日期格式
     *
     * @return 返回日期类型
     */
    @Bean
    public Converter<String, Date> string2Date() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if (StrUtil.isNotBlank(source)) {
                    DateTime parse = DateUtil.parse(source);
                    return parse;
                }
                return null;
            }
        };
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/home/**", buildConfig());
        return new CorsFilter(source);
    }

    /**
     * 定义错误页面
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            container.addErrorPages(error404Page);
        });
    }
}
