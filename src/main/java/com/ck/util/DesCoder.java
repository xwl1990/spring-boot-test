package com.ck.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DesCoder {

    /**
     * 单位长
     */
    private static final int UNIT_LENGTH = 8;

    public final static String KEY_ALGORITHM = "DESede";

    public final static String CHIPER_ALGORITHM = "DESede/ECB/NoPadding";

    /**
     * 加密
     * 
     * @param msg
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] msg, byte[] key) {
        byte[] des3Key = getKey(key);

        try {
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(des3Key, KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(fillGap(msg));
        } catch (Exception e) {
            throw new RuntimeException("Error when encrypt key, ermsg: " + e.getMessage(), e);
        }

    }

    /**
     * 解密
     * 
     * @param msg
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] msg, byte[] key) {
        byte[] des3Key = getKey(key);
        try {
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(des3Key, KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return removeGap(cipher.doFinal(msg));
        } catch (Exception e) {

            throw new RuntimeException("Error when drcrypt key, ermsg: " + e.getMessage(), e);
        }

    }

    /**
     * 得到3Deskey
     * 
     * @param key
     * @return
     */
    private static byte[] getKey(byte[] key) {
        if (key.length != 16) {
            throw new IllegalArgumentException(" key length is not 16 bytes.");
        }
        byte[] des3Key = new byte[24];
        System.arraycopy(key, 0, des3Key, 0, 16);
        System.arraycopy(key, 0, des3Key, 16, 8);
        return des3Key;
    }

    /**
     * 补位操作
     * 
     * @param srcBytes
     * @return
     */
    public static byte[] fillGap(byte[] srcBytes) {
        try {
            int len = srcBytes.length;
            int total = (len / 8 + 1) * 8;

            byte[] destBytes = new byte[total];
            System.arraycopy(srcBytes, 0, destBytes, 0, len);
            if (total - len == 1) {
                destBytes[len] = (byte) 0x80;
            } else {
                for (int i = len; i < total; i++) {
                    if (i == len) {
                        destBytes[i] = (byte) 0x80;
                    } else {
                        destBytes[i] = (byte) 0x00;
                    }

                }
            }
            return destBytes;
        } catch (Exception e) {
            throw new RuntimeException(" Error when msg fill gap " + e.getMessage(), e);
        }
    }

    /**
     * 移位操作
     * 
     * @param srcBytes
     * @return
     */
    public static byte[] removeGap(byte[] srcBytes) {
        byte[] desBytes;
        try {
            int len = srcBytes.length;
            int pos = 0;
            for (int i = len - 1; i >= len - 9; i--) {
                if (srcBytes[i] == -128) {
                    pos = i;
                    break;
                }
            }
            if (pos == 0) {
                desBytes = srcBytes;
            } else {
                desBytes = new byte[pos];
                System.arraycopy(srcBytes, 0, desBytes, 0, pos);
            }
            return desBytes;
        } catch (Exception e) {
            throw new RuntimeException(" Error when msg remove gap " + e.getMessage(), e);
        }

    }

    /**
     * 离散密钥
     * 
     * @param random
     * @param key
     * @return
     */
    public static byte[] disperse(byte[] factor, byte[] key) {
        if (factor == null || factor.length != 8) {
            throw new IllegalArgumentException(" factor length is not 8 bytes.");
        }
        byte[] firstBytes = new byte[8];
        byte[] secondBytes = new byte[8];
        byte[] ret = new byte[16];
        System.arraycopy(factor, 0, firstBytes, 0, 8);
        for (int i = 0; i < 8; i++) {
            secondBytes[i] = (byte) ~factor[i];
        }
        firstBytes = encrypt(firstBytes, key);
        secondBytes = encrypt(secondBytes, key);
        System.arraycopy(firstBytes, 0, ret, 0, 8);
        System.arraycopy(secondBytes, 0, ret, 8, 8);

        return ret;
    }

    /**
     * 3DES解密 先使用 key 对random进行离散，得出真正的用于解密的密钥 再使用得到的会话密钥 进行解密
     * 
     * @param random
     * @param key
     * @param data
     * @return
     */
    public static byte[] descryptDES3ByRandom(byte[] random, byte[] key, byte[] data) {
        byte[] keyb = disperse(random, key);
        return decrypt(data, keyb);
    }

    /**
     * 初始数据
     * 
     * @param srcBytes
     * @return
     */
    public static byte[] initData(byte[] srcBytes) {
        byte[] initBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
        int len = srcBytes.length;
        byte[] destBytes = new byte[len + 8];
        System.arraycopy(initBytes, 0, destBytes, 0, 8);
        System.arraycopy(srcBytes, 0, destBytes, 8, len);
        return destBytes;
    }

    /**
     * 初始数据和补位操作
     * 
     * @param srcBytes
     * @return
     */
    public static byte[] initAndFillGap(byte[] srcBytes) {
        return fillGap(initData(srcBytes));
    }

    public static byte[] subByte(byte b[], int start, int end) {
        int sublength = end - start;
        byte[] result = new byte[sublength];
        System.arraycopy(b, start, result, 0, sublength);
        return result;
    }

    /**
     * 8个字节做异或
     * 
     * @param b1
     * @param b2
     * @return
     */
    public static byte[] doXor(byte[] b1, byte[] b2) {
        int byte_length = 8;
        byte[] result = new byte[byte_length];

        if (b1.length != byte_length || b2.length != byte_length) {
            throw new IllegalArgumentException("Both byte array'length must = 8!");
        }

        for (int i = 0; i < b1.length; i++) {
            result[i] = (byte) (b1[i] ^ b2[i]);
            ;
        }

        return result;
    }

    /**
     * MAC签名计算
     * 
     * @param msg
     *            签名数据
     * @param mac
     *            长度是8位或者16位
     * @return
     */
    public static byte[] iboxMac(byte[] msg, byte[] mac) {
        if (null == msg || null == mac) {
            throw new NullPointerException("Msg or Mac is null value.");
        }
        int len = mac.length;
        if (len % 8 != 0 || len > 16 || len <= 0) {
            throw new IllegalArgumentException("Length of mac is ilegeal, expected length is 8 or 16 bytes.");
        }

        byte[] msg_whole = initAndFillGap(msg);
        int unit_number = msg_whole.length / UNIT_LENGTH;
        // 每8个字节做异或
        byte[] xorResult = subByte(msg_whole, 0, 8);
        for (int i = 1; i < unit_number; i++) {
            int start = i * UNIT_LENGTH;
            int end = start + UNIT_LENGTH;
            byte[] unit = subByte(msg_whole, start, end);
            xorResult = doXor(xorResult, unit);
            xorResult = encrypt(xorResult, subByte(mac, 0, 8));
        }
        if (len == 16) {
            xorResult = decrypt(xorResult, subByte(mac, 8, 16));
            xorResult = encrypt(xorResult, subByte(mac, 0, 8));
        }
        return xorResult;
    }

}
