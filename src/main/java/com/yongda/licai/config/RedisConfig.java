package com.yongda.licai.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis缓存配置
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/13-上午11:28
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 复写缓存key生成算法
     *
     * @return KeyGenerator
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":" + method.getName());
            for (Object obj : params) {
                sb.append(":" + obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 复写缓存管理器
     *
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        //cacheManager.setDefaultExpiration(900L);
        cacheManager.setExpires(this.expiresMap());
        return cacheManager;
    }

    /**
     * 将RedisTemplate暴露给spring上下文
     *
     * @param factory RedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean(value = "redisTemplate")
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 设置Redis序列化
     *
     * @param template RedisTemplate
     */
    private void setSerializer(StringRedisTemplate template) {
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
    }

    /**
     * 定义各缓存业务过期时间
     *
     * @return Map
     */
    private Map<String, Long> expiresMap() {
        Map<String, Long> map = new HashMap<>();
        map.put("CONFIG_CACHE", 900L);
        return map;
    }
}
