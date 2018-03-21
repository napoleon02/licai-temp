package com.yongda.licai.utils;

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;

/**
 * base64解码工具类
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKBase64Decoder {

    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 字节数组
     */
    public static byte[] decodeToBinaryData(String base64String) {
        return Base64.decodeBase64(base64String);
    }


    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 十六进制表示的字符串
     */
    public static String decodeToHex(String base64String) {
        return LKHexUtils.toHexFromBinaryData(decodeToBinaryData(base64String));
    }


    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 十六进制表示的字符串（字节表示，即两位十六进制表示一个字节）
     */
    public static String decodeToHexBytes(String base64String) {
        return LKHexUtils.toHexBytesFromBinaryData(decodeToBinaryData(base64String));
    }


    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 十进制表示的字符串
     */
    public static String decodeToDecimal(String base64String) {
        return decodeToBigInteger(base64String).toString();
    }


    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 大整型
     */
    public static BigInteger decodeToBigInteger(String base64String) {
        return new BigInteger(decodeToBinaryData(base64String));
    }


    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 长整型
     */
    public static long decodeToLong(String base64String) {
        return new BigInteger(decodeToBinaryData(base64String)).longValue();
    }


    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 整型
     */
    public static int decodeToInteger(String base64String) {
        return new BigInteger(decodeToBinaryData(base64String)).intValue();
    }


    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 短整型
     */
    public static short decodeToShort(String base64String) {
        return new BigInteger(decodeToBinaryData(base64String)).shortValue();
    }


    /**
     * base64解码
     *
     * @param base64String base64编码的字符串
     * @return 字节型
     */
    public static byte decodeToByte(String base64String) {
        return new BigInteger(decodeToBinaryData(base64String)).byteValue();
    }

}
