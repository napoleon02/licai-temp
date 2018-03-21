package com.yongda.licai.config;

import com.yongda.licai.thirdparty.member.MemberClient;
import com.yongda.licai.thirdparty.ydpay.YongDaPayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 系统Bean配置
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/31-下午1:35
 */
@Configuration
public class SystemBeanConfig {

    @Value(value = "${member.host}")
    private String memberHost;

    @Value(value = "${member.publicKey}")
    private String memberPublicKey;

    @Resource(name = "environment")
    private Environment environment;

    @Bean
    public MemberClient createMemberClient() {
        return new MemberClient(memberHost, memberPublicKey);
    }

    @Bean
    public YongDaPayClient yongDaPayClient() throws Exception {
        return new YongDaPayClient()
                .setHost(environment.getProperty("yongdapay.host"))
                .setCerPath(environment.getProperty("yongdapay.cerPath"))
                .setPfxPath(environment.getProperty("yongdapay.pfxPath"))
                .setCvmPath(environment.getProperty("yongdapay.cvmPath"))
                .setPfxPwd(environment.getProperty("yongdapay.pfxPwd"))
                .setPartnerId(environment.getProperty("yongdapay.partnerId"))
                .setSellerId(environment.getProperty("yongdapay.sellerId"))
                .setSignType("ITRUSSRV")
                .setCharset("UTF-8")
                .build();
    }

    @Bean
    public Executor myExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(16);
        executor.setThreadNamePrefix("myExecutor-");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //对拒绝task的处理策略
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }

}
