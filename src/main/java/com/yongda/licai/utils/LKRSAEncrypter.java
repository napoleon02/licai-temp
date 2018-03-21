package com.yongda.licai.utils;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密工具类
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKRSAEncrypter extends LKRSA {

    /**
     * 使用十六进制表示的公钥加密成十进制表示的密文
     *
     * @param plaintext 明文
     * @param publicKey 十六进制表示的公钥
     * @return 十进制表示的密文
     */
    public static String encryptToDeciamlWithHexPublicKey(String plaintext, String publicKey) {
        try {
            RSAPublicKey rsaPubKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(LKHexUtils.toBytesFromHex(publicKey)));

            BigInteger coded = new BigInteger(plaintext.getBytes("UTF-8")).modPow(rsaPubKey.getPublicExponent(), rsaPubKey.getModulus());

            return coded.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用十六进制表示的公钥加密成Base64表示的密文
     *
     * @param plaintext 明文
     * @param publicKey 十六进制表示的公钥
     * @return Base64表示的密文
     */
    public static String encryptToBase64WithHexPublicKey(String plaintext, String publicKey) {
        return LKBase64Encoder.encodeWithDecimal(encryptToDeciamlWithHexPublicKey(plaintext, publicKey));
    }


    /**
     * 使用Base64表示的公钥加密成十进制表示的密文
     *
     * @param plaintext 明文
     * @param publicKey Base64表示的公钥
     * @return 十进制表示的密文
     */
    public static String encryptToDeciamlWithBase64PublicKey(String plaintext, String publicKey) {
        return encryptToDeciamlWithHexPublicKey(plaintext, LKBase64Decoder.decodeToHex(publicKey));
    }


    /**
     * 使用Base64表示的公钥加密成Base64表示的密文
     *
     * @param plaintext 明文
     * @param publicKey Base64表示的公钥
     * @return Base64表示的密文
     */
    public static String encryptToBase64WithBase64PublicKey(String plaintext, String publicKey) {
        return LKBase64Encoder.encodeWithDecimal(encryptToDeciamlWithHexPublicKey(plaintext, LKBase64Decoder.decodeToHex(publicKey)));
    }

}
