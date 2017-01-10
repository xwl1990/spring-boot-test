
package com.ck.util;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA 加解密
 * 
 * @author: wangyanhui
 * @since: 2015/07/21   
 */
public class RSACoder {

    /**
     * 算法
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 算法/工作模式/填充方式
     */
    public final static String CHIPER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    /**
     * 密钥长度
     */
    public static final int KEY_SIZE = 1024;

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     * 
     * @param data
     *            加密数据
     * @param privateKey
     *            私钥
     * @return
     */
    public static byte[] sign(byte[] data, byte[] privateKey) {
        try {
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取私钥匙对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data);

            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("Error when verify sign, ermsg: " + e.getMessage(), e);
        }
    }

    /**
     * 校验数字签名
     * 
     * @param data
     *            数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     * @return
     */
    public static boolean verifyByPublicKey(byte[] data, byte[] publicKey, byte[] sign) {

        try {
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取公钥匙对象
            PublicKey pubKey = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(data);

            // 验证签名是否正常
            return signature.verify(sign);
        } catch (Exception e) {
            throw new RuntimeException("Error when verify sign, ermsg: " + e.getMessage(), e);
        }

    }

    /**
     * 私钥解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) {
        try {
            // 取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            // 生成私钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error when decrypt key, ermsg: " + e.getMessage(), e);
        }
    }

    /**
     * 公钥解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) {
        try {
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            // 生成公钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error when decrypt key, ermsg: " + e.getMessage(), e);
        }
    }

    /**
     * 公钥加密
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) {
        try {
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            // 生成公钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error when encrypt key, ermsg: " + e.getMessage(), e);
        }
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) {
        try {
            // 取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            // 生成私钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error when encrypt key, ermsg: " + e.getMessage(), e);
        }
    }

    public static Map<String, Object> initKey() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(KEY_SIZE);

            KeyPair keyPair = keyPairGen.generateKeyPair();

            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            Map<String, Object> keyMap = new HashMap<String, Object>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            throw new RuntimeException("Error when init key, ermsg: " + e.getMessage(), e);
        }
    }

    public static byte[] intToBCD(int len) {
        byte[] bcdByte = new byte[2];
        bcdByte[0] = (byte) ((len / 100) / 10 * 0x10 + (len / 100) % 10);
        bcdByte[1] = (byte) ((len % 100) / 10 * 0x10 + (len % 100) % 10);
        return bcdByte;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        /*
         * Map<String, Object> map = initKey(); byte[] pri =
         * ((Key)map.get(PRIVATE_KEY)).getEncoded(); String priKey =
         * EncodeUtil.bytesToHexString(pri); System.out.println(priKey);
         * System.out.println("--------"); byte[] pub =
         * ((Key)map.get(PUBLIC_KEY)).getEncoded(); String pubKey =
         * EncodeUtil.bytesToHexString(pub); System.out.println(pubKey);
         */

        //		String data = "1234567890AVBCDS";
        //		byte[] result = encryptByPrivateKey(data.getBytes(), EncodeUtil
        //				.hexStringToByte(Constants.RSAPRIKEY));
        //
        //		byte[] deRe = decryptByPublicKey(result, EncodeUtil
        //				.hexStringToByte(Constants.RSAPUBKEY));
        //
        //		System.out.println(EncodeUtil.bytesToHexString(result));
        //		System.out.println("-------");
        //		System.out.println(new String(deRe, "UTF-8"));
        // 生成之后，采用不同的方式来转换成16进制字符串
        // iphone生成方式
        /*
         * System.out.println(EncodeUtil.bytesToHexString(privateKey.getModulus()
         * .toByteArray()));
         * System.out.println(EncodeUtil.bytesToHexString(privateKey
         * .getPrivateExponent().toByteArray()));
         */
        // java生成方式
        /*
         * System.out.println(EncodeUtil.bytesToHexString(publicKey.getEncoded())
         * );
         * System.out.println(EncodeUtil.bytesToHexString(privateKey.getEncoded
         * ()));
         */

        //		Map keyMap = initKey();
        //		RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
        //		RSAPrivateKey privateKey = (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
        //		System.out.println("public:"+EncodeUtil.bytesToHexString(publicKey.getEncoded()));
        //		System.out.println("private:"+EncodeUtil.bytesToHexString(privateKey.getEncoded()));
    }

}
