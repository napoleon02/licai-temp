package com.yongda.licai.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Random;

/**
 * RSA解密工具类
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKRSADecrypter {
    private static Logger log1 = LoggerFactory.getLogger("LKRSADecrypter");

    /**
     * 使用十六进制表示的私钥从十进制表示的密文中解密出明文
     *
     * @param ciphertext 十进制表示的密文
     * @param privateKey 十六进制表示的私钥
     * @return 明文
     */
    public static String decryptFromDecimalWithHexPrivateKey(String ciphertext, String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            log1.info("keyFactory值：" + keyFactory);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(LKHexUtils.toBytesFromHex(privateKey)));
            log1.info("rsaPrivateKey值：" + rsaPrivateKey);
            //return new String(new BigInteger(ciphertext).modPow(rsaPrivateKey.getPrivateExponent(), rsaPrivateKey.getModulus()).toByteArray(), "UTF-8");
            //以下是进行的修改
            log1.info("解密值2：" + new String(new BigInteger(decodeToBigInteger(ciphertext).toString()).modPow(rsaPrivateKey.getPrivateExponent(), rsaPrivateKey.getModulus()).toByteArray(), "gbk"));
            log1.info("解密值：" + new String(new BigInteger(decodeToBigInteger(ciphertext).toString()).modPow(rsaPrivateKey.getPrivateExponent(), rsaPrivateKey.getModulus()).toByteArray(), "UTF-8"));
            return new String(new BigInteger(decodeToBigInteger(ciphertext).toString()).modPow(rsaPrivateKey.getPrivateExponent(), rsaPrivateKey.getModulus()).toByteArray(), "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 使用十六进制表示的私钥从Base64表示的密文中解密出明文
     *
     * @param ciphertext Base64表示的密文
     * @param privateKey 十六进制表示的私钥
     * @return 明文
     */
    public static String decryptFromBase64WithHexPrivateKey(String ciphertext, String privateKey) {
        //return decryptFromDecimalWithHexPrivateKey(LKBase64Decoder.decodeToDecimal(ciphertext), privateKey);
        //以下是针对解密修改
        log1.info("使用十六进制表示的私钥从Base64表示的密文中解密出明文");
        return decryptFromDecimalWithHexPrivateKey(ciphertext, privateKey);
    }

    //以下针对RSA解密进行的修改
    public static BigInteger decodeToBigInteger(String base64String) {
        byte[] decodeToBinaryData = Base64.decodeBase64(base64String);
        if (ArrayUtils.isNotEmpty(decodeToBinaryData)) {
            if (decodeToBinaryData[0] < 0) {
                // 这里是问题的关键，就是因为默认是把每一个字节都直接转换成了字节得到的数组。
                // 但是后面new BigInteger的时候是有符号的，所以本来无符号的大数，在第一个字节存储的值超过128时，就使得整个数字变成了有符号的负数，导致解密失败，也就是因为这个原因，所以不是所有的加密解密都会出现这个情况。
                decodeToBinaryData = ArrayUtils.add(decodeToBinaryData, 0, (byte) 0);
            }
        }
        return new BigInteger(decodeToBinaryData);
    }

    /**
     * 使用Base64表示的私钥从十进制表示的密文中解密出明文
     *
     * @param ciphertext 十进制表示的密文
     * @param privateKey Base64表示的私钥
     * @return 明文
     */
    public static String decryptFromDecialWithBase64PrivateKey(String ciphertext, String privateKey) {
        return decryptFromDecimalWithHexPrivateKey(ciphertext, LKBase64Decoder.decodeToHex(privateKey));
    }


    /**
     * 使用Base64表示的私钥从Base64表示的密文中解密出明文
     *
     * @param ciphertext Base64表示的密文
     * @param privateKey Base64表示的私钥
     * @return 明文
     */
    public static String decryptFromBase64WithBase64PrivateKey(String ciphertext, String privateKey) {
        return decryptFromDecimalWithHexPrivateKey(LKBase64Decoder.decodeToDecimal(ciphertext), LKBase64Decoder.decodeToHex(privateKey));
    }


    public static void main(String[] args) {
        /*String privateKey = "30820276020100300d06092a864886f70d0101010500048202603082025c020100028181009bb9b0240bbaacd5e408b5a74d206e7eeae149f3c9e708dc5641574532296dde1893b03db02bdfb19f5960c4662509f3c1587b58aa8f1d3489ea8ac63f04156de6680000cd049884c764edfb43aff0fde253d000b28443f4001dce3f3970f50f3c9780914232ac32c4419096831c0cf85bd4972d640cf31026ff98223bd6489f02030100010281801f1514244db707f2755e8bab860878259b0a36b193562afd97b5d90e75b1b13d48588a2ff5eefbea3f2d1ed474b2e5e6a26bdfcad5d854f2fca834e4d70520a58c387b7af58ce67e1ed3e6803ff5149ee34ca445b12723b2f2004a5d8f8e9a948823550322577da2d10f29577c373140353f4a3aff3cfca28a7ed19b1ab48831024100cf9785929a3a645a1751e0acc286f1f4c0d0e5a19d8c51a3729e194690d2590c87e0eeabdcff1e5a3e00d6a888ac5bf8c5d2bfb8006e8144ae246f96962d3fc9024100c009ec52455866b77380f3e6c0c6e5f2734e1f7f997b996ca45f3b8540f3bce2d9d23d4dc6d8654f3e8721f3f9b7efa2a2f3f9f99df05e59e214343b02d789270240095dcfd40e8b65edbeb19e0e8d74634464d2c819a3af2a1bd2d71952dac3f2eaa2d2de51f8d5b5fbe2624d4d2b65837cd5082e485214aa567bf8fee3ef80b92902401ec538459270f0bc7258763c42255c90f5a2cdef3f238bd8d9999ccae43669cc9b84516855f5347e7711660256bfff38bb0d86bf556c3f61fd94a92dcf6dc3bf024100970fc4faffb504280e72040b91b69b875ed80e319831ebf537f2a5b9ede5692a370db2f21140b75314817cb6dff7ae2c34d6369fb8285ff24d84c1e7edfa1f37";
        String data       = "azbsNlktELmDr%2BWF7yeBlfX7DbtD%2FSRTbm8wE0exKmnXmnWzCRlfinC1AD3Z0supt3yXG7QJfgjk%2FJ9cEakuMKj7J7z5Axi9m0UmHxb9R6s2Htz19rtzouIuB9hHI5cxMaCWc769tVRnP0prT%2BdmZi09Fp%2B6H6xNVChEm%2Bw871k";
		String retuls     = decryptFromBase64WithHexPrivateKey(data,privateKey);
		System.out.println(retuls);*/
        test();
    }

    public static void test() {
        String plaintext = null;
        for (int i = 0; i < 900; i++) {
            plaintext = getCharAndNumr(7);
        }

        System.out.println("plaintext=>" + plaintext);

        // LKKeyPair keyPair = LKRSAGenerator.generatKeys(1024);

        // System.out.println();
        // String publicKeyHex = keyPair.getPublicKey();
        // System.out.println("publicKeyHex=>" + publicKeyHex);
        // String privateKeyHex = keyPair.getPrivateKey();
        // System.out.println("privateKeyHex=>" + privateKeyHex);

        System.out.println();
        String publicKeyHex = "30819f300d06092a864886f70d010101050003818d00308189028181009bb9b0240bbaacd5e408b5a74d206e7eeae149f3c9e708dc5641574532296dde1893b03db02bdfb19f5960c4662509f3c1587b58aa8f1d3489ea8ac63f04156de6680000cd049884c764edfb43aff0fde253d000b28443f4001dce3f3970f50f3c9780914232ac32c4419096831c0cf85bd4972d640cf31026ff98223bd6489f0203010001";
        //System.out.println("publicKeyHex=>" + publicKeyHex);
        String privateKeyHex = "30820276020100300d06092a864886f70d0101010500048202603082025c020100028181009bb9b0240bbaacd5e408b5a74d206e7eeae149f3c9e708dc5641574532296dde1893b03db02bdfb19f5960c4662509f3c1587b58aa8f1d3489ea8ac63f04156de6680000cd049884c764edfb43aff0fde253d000b28443f4001dce3f3970f50f3c9780914232ac32c4419096831c0cf85bd4972d640cf31026ff98223bd6489f02030100010281801f1514244db707f2755e8bab860878259b0a36b193562afd97b5d90e75b1b13d48588a2ff5eefbea3f2d1ed474b2e5e6a26bdfcad5d854f2fca834e4d70520a58c387b7af58ce67e1ed3e6803ff5149ee34ca445b12723b2f2004a5d8f8e9a948823550322577da2d10f29577c373140353f4a3aff3cfca28a7ed19b1ab48831024100cf9785929a3a645a1751e0acc286f1f4c0d0e5a19d8c51a3729e194690d2590c87e0eeabdcff1e5a3e00d6a888ac5bf8c5d2bfb8006e8144ae246f96962d3fc9024100c009ec52455866b77380f3e6c0c6e5f2734e1f7f997b996ca45f3b8540f3bce2d9d23d4dc6d8654f3e8721f3f9b7efa2a2f3f9f99df05e59e214343b02d789270240095dcfd40e8b65edbeb19e0e8d74634464d2c819a3af2a1bd2d71952dac3f2eaa2d2de51f8d5b5fbe2624d4d2b65837cd5082e485214aa567bf8fee3ef80b92902401ec538459270f0bc7258763c42255c90f5a2cdef3f238bd8d9999ccae43669cc9b84516855f5347e7711660256bfff38bb0d86bf556c3f61fd94a92dcf6dc3bf024100970fc4faffb504280e72040b91b69b875ed80e319831ebf537f2a5b9ede5692a370db2f21140b75314817cb6dff7ae2c34d6369fb8285ff24d84c1e7edfa1f37";
        //System.out.println("privateKeyHex=>" + privateKeyHex);

		/*System.out.println();
		String publicKeyBase64 = LKBase64Encoder.encodeWithHex(publicKeyHex);
		System.out.println("publicKeyBase64=>" + publicKeyBase64);
		String privateKeyBase64 = LKBase64Encoder.encodeWithHex(privateKeyHex);
		System.out.println("privateKeyBase64=>" + privateKeyBase64);

		System.out.println();
		String encryptToDeciamlWithHexPublicKey = LKRSAEncrypter.encryptToDeciamlWithHexPublicKey(plaintext, publicKeyHex);
		System.out.println("encryptToDeciamlWithHexPublicKey=>" + encryptToDeciamlWithHexPublicKey);
		String decryptFromDecimalWithHexPrivateKey = LKRSADecrypter.decryptFromDecimalWithHexPrivateKey(encryptToDeciamlWithHexPublicKey, privateKeyHex);
		System.out.println("decryptFromDecimalWithHexPrivateKey=>" + decryptFromDecimalWithHexPrivateKey);
		String decryptFromDecialWithBase64PrivateKey = LKRSADecrypter.decryptFromDecialWithBase64PrivateKey(encryptToDeciamlWithHexPublicKey, privateKeyBase64);
		System.out.println("decryptFromDecialWithBase64PrivateKey=>" + decryptFromDecialWithBase64PrivateKey);

		System.out.println();
		String encryptToDeciamlWithBase64PublicKey = LKRSAEncrypter.encryptToDeciamlWithBase64PublicKey(plaintext, publicKeyBase64);
		System.out.println("encryptToDeciamlWithBase64PublicKey=>" + encryptToDeciamlWithBase64PublicKey);
		String decryptFromDecimalWithHexPrivateKey2 = LKRSADecrypter.decryptFromDecimalWithHexPrivateKey(encryptToDeciamlWithBase64PublicKey, privateKeyHex);
		System.out.println("decryptFromDecimalWithHexPrivateKey2=>" + decryptFromDecimalWithHexPrivateKey2);
		String decryptFromDecialWithBase64PrivateKey2 = LKRSADecrypter.decryptFromDecialWithBase64PrivateKey(encryptToDeciamlWithBase64PublicKey, privateKeyBase64);
		System.out.println("decryptFromDecialWithBase64PrivateKey2=>" + decryptFromDecialWithBase64PrivateKey2);*/

        System.out.println();
        String encryptToBase64WithHexPublicKey = LKRSAEncrypter.encryptToBase64WithHexPublicKey(plaintext, publicKeyHex);
        System.out.println("encryptToBase64WithHexPublicKey=>" + encryptToBase64WithHexPublicKey);

        String decryptFromBase64WithHexPrivateKey = LKRSADecrypter.decryptFromBase64WithHexPrivateKey(encryptToBase64WithHexPublicKey, privateKeyHex);
        System.out.println("decryptFromBase64WithHexPrivateKey=>" + decryptFromBase64WithHexPrivateKey);
		
		/*if(decryptFromBase64WithHexPrivateKey.length()){
			
		}*/
		/*String decryptFromBase64WithBase64PrivateKey = LKRSADecrypter.decryptFromBase64WithBase64PrivateKey(encryptToBase64WithHexPublicKey, privateKeyBase64);
		System.out.println("decryptFromBase64WithBase64PrivateKey=>" + decryptFromBase64WithBase64PrivateKey);*/

		/*System.out.println();
		String encryptToBase64WithBase64PublicKey = LKRSAEncrypter.encryptToBase64WithBase64PublicKey(plaintext, publicKeyBase64);
		System.out.println("encryptToBase64WithBase64PublicKey=>" + encryptToBase64WithBase64PublicKey);
		String decryptFromBase64WithHexPrivateKey2 = LKRSADecrypter.decryptFromBase64WithHexPrivateKey(encryptToBase64WithBase64PublicKey, privateKeyHex);
		System.out.println("decryptFromBase64WithHexPrivateKey2=>" + decryptFromBase64WithHexPrivateKey2);
		String decryptFromBase64WithBase64PrivateKey2 = LKRSADecrypter.decryptFromBase64WithBase64PrivateKey(encryptToBase64WithBase64PublicKey, privateKeyBase64);
		System.out.println("decryptFromBase64WithBase64PrivateKey2=>" + decryptFromBase64WithBase64PrivateKey2);*/
    }


    /**
     * java生成随机数字和字母组合
     *
     * @param length[生成随机数的长度]
     * @return
     */
    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
