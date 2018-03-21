package com.yongda.licai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证签名注解
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/14-下午1:32
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSignature {
}
