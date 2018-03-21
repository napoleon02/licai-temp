package com.yongda.licai.utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

/**
 * RSA工具类
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
class LKRSA {

    protected static KeyFactory keyFactory;

    static {
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
