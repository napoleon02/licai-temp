package com.yongda.licai.utils;

import com.xiaoleilu.hutool.crypto.digest.DigestUtil;

/**
 * 密码工具类
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/26-下午1:22
 */
public class PwdUtils {

    /**
     * 构建密码
     *
     * @param password 密码明文
     * @param salt     密码盐
     * @return 加密后的密码
     */
    public static String buildPwd(String password, String salt) {
        return DigestUtil.md5Hex(salt + password);
    }

}
