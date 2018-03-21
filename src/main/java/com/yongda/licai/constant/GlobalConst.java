package com.yongda.licai.constant;

/**
 * 系统全局常量
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/22-下午1:19
 */
public interface GlobalConst {

    /**
     * 前台用户登录的SESSION
     */
    String USER_SESSION_KEY = "USER_SESSION_KEY";

    /**
     * 后台管理员登录的SESSION
     */
    String ADMIN_SESSION_KEY = "ADMIN_SESSION_KEY";

    /**
     * 开发环境
     */
    String PROFILE_DEV = "dev";

    /**
     * 测试环境
     */
    String PROFILE_TEST = "test";

    /**
     * 生产环境
     */
    String PROFILE_PROD = "prod";

    /**
     * 合同编号前缀
     */
    String CONTRACT_NUMBER_PREFIX = "JQGL";
}
