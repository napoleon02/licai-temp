package com.yongda.licai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限资源
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/2-下午1:54
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthResource {

    /**
     * 资源编码
     *
     * @return String
     */
    String code();
}
