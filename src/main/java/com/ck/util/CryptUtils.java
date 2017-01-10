package com.ck.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

/**
 * 提供各种加密解密的算法工具
 * The class CryptUtils.
 *
 * Description: 
 *
 * @author: xieweili
 * @since: 2017年1月10日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class CryptUtils {

    private static final Logger logger = LoggerFactory.getLogger(CryptUtils.class);

    /**
     * MD5 加密
     * 
     * @param String
     *            info 需要MD5加密的字符串
     * 
     * @return String result MD5加密后返回的结果
     */
    public static String encryptToMD5(byte[] info) {
        // MessageDegist计算摘要后 得到的是Byte数组
        byte[] digesta = null;
        try {
            // 获取消息摘要MessageDigest抽象类的实例
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            // 添加需要进行计算摘要的对象（字节数组）
            mDigest.update(info);
            // 计算摘要
            digesta = mDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }

        // 将字节数组转换为String并返回
        return EncodeUtils.bytesToHexString(digesta);
    }

    /**
     * SHA-1 加密
     * 
     * @param String
     *            info 需要SHA加密的字符穿
     * 
     * @return String result SHA加密后返回的结果
     */
    public static String encryptToSHA(String info) {
        byte[] digesta = null;
        try {
            // 获取消息摘要MessageDigest抽象类的实例
            MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
            // 添加需要进行计算摘要的对象（字节数组）
            mDigest.update(info.getBytes());
            // 计算摘要
            digesta = mDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("no such algorith exception, " + e.getMessage(), e);

        }

        // 将字节数组转换为String并返回
        return EncodeUtils.bytesToHexString(digesta);
    }

 

    /**
     * 对字符串进行3DES加密方法
     * 
     * @param src
     * @param key
     * @return
     */
    public static String encrypt3DES(String src, String key) {
        try {
            return EncodeUtils.bytesToHexString(DesCoder.encrypt(src.getBytes(), key.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(" Error when encrypt msg " + e.getMessage(), e);
        }

    }

    /**
     * mac认证
     * 
     * @param msg
     * @param macKey
     * @param result
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean verify(String msg, String macKey, String result) {
        boolean verifyResult = false;
        try {
            byte[] bArray = DesCoder.iboxMac(msg.getBytes("UTF-8"), macKey.getBytes("UTF-8"));
            if (EncodeUtils.bytesToHexString(bArray).equalsIgnoreCase(result)) {
                verifyResult = true;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(" Error when verify msg " + e.getMessage(), e);
        }
        return verifyResult;
    }

    /**
     * rsa解密
     * 
     * @param data
     * @param publicKey
     * @param iboxNo
     * @return
     */
    public static String decryptRsaByPublicKey(String data, String publicKey) {
        try {
            byte[] decryptData = RSACoder.decryptByPublicKey(data.getBytes("UTF-8"), publicKey.getBytes("UTF-8"));
            String plain = new String(decryptData, "UTF-8");

            return plain;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(" Error when decrypt errmsg: " + e.getMessage(), e);
        }
    }

    public static String base64Decode(String base64Str) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] tempByte = decoder.decodeBuffer(base64Str);

        return new String(tempByte);
    }

    public static void main(String[] args) {
        System.out.print(CryptUtils.encryptToMD5(("123456QWasHuLQ").getBytes()));
        //A1DC825387A574774551563AF2590C2A
    }

}
