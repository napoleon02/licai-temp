package com.yongda.licai.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigInteger;

/**
 * 十六进制工具类
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public final class LKHexUtils {

    /** ===============================================十进制转换为十六进制=============================================== */

    /**
     * 十进制数字转换为十六进制字符串
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexFromDecimal(String decimal) {
        return new BigInteger(decimal).toString(16);
    }


    /**
     * 十进制数字转换为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String toHexFromBinaryData(byte[] bytes) {
        return new BigInteger(bytes).toString(16);
    }


    /**
     * 十进制数字转换为十六进制字符串
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexFromBigInteger(BigInteger decimal) {
        return decimal.toString(16);
    }


    /**
     * 十进制数字转换为十六进制字符串
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexFromLong(long decimal) {
        return Long.toHexString(decimal);
    }


    /**
     * 十进制数字转换为十六进制字符串
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexFromInteger(int decimal) {
        return Integer.toHexString(decimal);
    }


    /**
     * 十进制数字转换为十六进制字符串
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexFromShort(short decimal) {
        return Integer.toHexString(decimal);
    }


    /**
     * 十进制数字转换为十六进制字符串
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexFromByte(byte decimal) {
        return Integer.toHexString(decimal);
    }


    /** ===============================================十进制转换为十六进制=============================================== */

    /**
     * 不足位补0
     *
     * @param hex 十六进制表示的字符串
     * @return 十六进制字符串（字节表示，即两位十六进制表示一个字节）
     */
    private static String fillZero(String hex) {
        if (hex == null || "".equals(hex)) {
            return hex;
        }
        if (hex.length() % 2 != 0) {
            return "0" + hex;
        }
        return hex;
    }


    /**
     * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexBytesFromDecimal(String decimal) {
        return toHexBytesFromBinaryData(new BigInteger(decimal).toByteArray());
    }


    /**
     * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String toHexBytesFromBinaryData(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(toHexBytesFromByte(bytes[i]));
        }
        return sb.toString();
    }


    /**
     * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexBytesFromBigInteger(BigInteger decimal) {
        return toHexBytesFromBinaryData(decimal.toByteArray());
    }


    /**
     * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexBytesFromLong(long decimal) {
        return toHexBytesFromBinaryData(new BigInteger(String.valueOf(decimal)).toByteArray());
    }


    /**
     * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexBytesFromInteger(int decimal) {
        return toHexBytesFromBinaryData(new BigInteger(String.valueOf(decimal)).toByteArray());
    }


    /**
     * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexBytesFromShort(short decimal) {
        return toHexBytesFromBinaryData(new BigInteger(String.valueOf(decimal)).toByteArray());
    }


    /**
     * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
     *
     * @param decimal 十进制数字
     * @return 十六进制字符串
     */
    public static String toHexBytesFromByte(byte decimal) {
        StringBuilder sb = new StringBuilder();
        int x = decimal;
        if (x < 0) {
            x += 256;
        }
        if (x < 16) {
            sb.append("0");
        }
        sb.append(Integer.toHexString(x));
        return sb.toString();
    }


    /** ===============================================十六进制转换为十进制=============================================== */

    /**
     * 十六进制字符串转换为十进制数字
     *
     * @param hex 十六进制字符串
     * @return 十进制数字
     */
    public static String toDecimalFromHex(String hex) {
        return toBigIntegerFromHex(hex).toString();
    }


    /**
     * 十六进制字符串转换为十进制数字
     *
     * @param hex 十六进制字符串
     * @return 十进制数字
     */
    public static BigInteger toBigIntegerFromHex(String hex) {
        return new BigInteger(hex, 16);
    }


    /**
     * 十六进制字符串转换为十进制数字
     *
     * @param hex 十六进制字符串
     * @return 十进制数字
     */
    public static long toLongFromHex(String hex) {
        return toBigIntegerFromHex(hex).longValue();
    }


    /**
     * 十六进制字符串转换为十进制数字
     *
     * @param hex 十六进制字符串
     * @return 十进制数字
     */
    public static int toIntegerFromHex(String hex) {
        return toBigIntegerFromHex(hex).intValue();
    }


    /**
     * 十六进制字符串转换为十进制数字
     *
     * @param hex 十六进制字符串
     * @return 十进制数字
     */
    public static short toShortFromHex(String hex) {
        return toBigIntegerFromHex(hex).shortValue();
    }


    /**
     * 十六进制字符串转换为十进制数字
     *
     * @param hex 十六进制字符串
     * @return 十进制数字
     */
    public static byte toByteFromHex(String hex) {
        return toBigIntegerFromHex(hex).byteValue();
    }


    /** ===============================================转换为字节数组=============================================== */

    /**
     * 转换为字节数组
     *
     * @param hex 十六进制字符串
     * @return 字节数组
     */
    public static byte[] toBytesFromHex(String hex) {
        hex = fillZero(hex);
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0, start = 0; i < bytes.length; i++, start = i * 2) {
            bytes[i] = toByteFromHex(hex.substring(start, start + 2));
        }
        return bytes;
    }


    /**
     * 转换为字节数组
     *
     * @param decimal 十进制字符串
     * @return 字节数组
     */
    public static byte[] toBytesFromDecimal(String decimal) {
        return toBytesFromHex(toHexFromDecimal(decimal));
    }


    /**
     * 转换为字节数组
     *
     * @param decimal 数字
     * @return 字节数组
     */
    public static byte[] toBytesFromBigInteger(BigInteger decimal) {
        return toBytesFromHex(toHexFromBigInteger(decimal));
    }


    /**
     * 转换为字节数组
     *
     * @param decimal 数字
     * @return 字节数组
     */
    public static byte[] toBytesFromLong(long decimal) {
        return toBytesFromHex(toHexFromLong(decimal));
    }


    /**
     * 转换为字节数组
     *
     * @param decimal 数字
     * @return 字节数组
     */
    public static byte[] toBytesFromInteger(int decimal) {
        return toBytesFromHex(toHexFromInteger(decimal));
    }


    /**
     * 转换为字节数组
     *
     * @param decimal 数字
     * @return 字节数组
     */
    public static byte[] toBytesFromShort(short decimal) {
        return toBytesFromHex(toHexFromShort(decimal));
    }


    /**
     * 转换为字节数组
     *
     * @param decimal 数字
     * @return 字节数组
     */
    public static byte[] toBytesFromByte(byte decimal) {
        return toBytesFromHex(toHexFromByte(decimal));
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
}
