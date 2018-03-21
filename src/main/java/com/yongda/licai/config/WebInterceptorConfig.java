package com.yongda.licai.config;

import com.yongda.licai.system.web.interceptor.AdminLoginedInterceptor;
import com.yongda.licai.system.web.interceptor.HomeLoginedInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WEB拦截器配置
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/14-下午1:28
 */
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //如果需要定义多个拦截器往里面添加即可
        //registry.addInterceptor(apiCheckSignatureInterceptor()).addPathPatterns("/api/**");

        //后台登录状态校验拦截器
        registry.addInterceptor(adminLoginedInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login/**");

        //前台登录状态校验拦截器
        registry.addInterceptor(homeLoginedInterceptor())
                .addPathPatterns("/home/**");

        super.addInterceptors(registry);
    }

    @Bean
    public AdminLoginedInterceptor adminLoginedInterceptor() {
        return new AdminLoginedInterceptor();
    }

    /*@Bean
    public ApiCheckSignatureInterceptor apiCheckSignatureInterceptor() {
        return new ApiCheckSignatureInterceptor();
    }*/

    @Bean
    public HomeLoginedInterceptor homeLoginedInterceptor() {
        return new HomeLoginedInterceptor();
    }
}
