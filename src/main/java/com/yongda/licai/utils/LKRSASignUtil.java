package com.yongda.licai.utils;

import com.xiaoleilu.hutool.util.HexUtil;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class LKRSASignUtil {

    /**
     * 公钥加密
     *
     * @param plaintext 明文
     * @param pubKey    公钥
     * @return base64字符串
     */
    public static String encode(String plaintext, String pubKey) {
        String encryptToBase64WithHexPublicKey = LKRSAEncrypter.encryptToBase64WithHexPublicKey(plaintext, pubKey);
        return encryptToBase64WithHexPublicKey;
    }

    /**
     * 私钥解密
     *
     * @param base64Str base64字符串
     * @param priKey    私钥字符串
     * @return 解密后的字符串
     */
    public static String decode(String base64Str, String priKey) {
        try {
            byte[] pribyte = HexUtil.decodeHex(priKey.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);

            // 获得私钥参数
            BigInteger n = privateKey.getModulus();
            BigInteger d = privateKey.getPrivateExponent();
            BigInteger big = new BigInteger(HexUtil.encodeHexStr(com.xiaoleilu.hutool.lang.Base64.decode(base64Str)), 16);
            // 密文
            BigInteger coded = new BigInteger(big.toByteArray());
            BigInteger m = coded.modPow(d, n);
            // 打印解密结果
            byte[] result = m.toByteArray();
            String str = new String(result, "UTF-8");
            return str;
        } catch (Exception e) {
            return null;
        }
    }

}
