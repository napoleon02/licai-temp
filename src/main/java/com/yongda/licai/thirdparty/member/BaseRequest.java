package com.yongda.licai.thirdparty.member;

import java.util.Map;

/**
 * 请求基类
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/1/30-下午2:57
 */
public interface BaseRequest<T extends BaseResponse> {

    String getBiz();

    /**
     * 获取请求参数
     *
     * @param publickey 公钥
     * @return Map
     */
    Map<String, Object> getParams(String publickey);

    /**
     * 获取目标类型
     *
     * @return Class
     */
    Class<T> getTargetClass();
}
