package com.nuls.io.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * url的AES加密 解密（加密后在地址栏上没有“+”等特殊符号）
 *
 * @author ambition
 * @version $Id: AESUrlCodec.java, v 0.1 2016年5月23日 上午10:13:33 ambition Exp $
 */
public class AESUrlCodec {
    /**
     * 加密和解密的key
     */
    private final static String key = "cnambition123456";
    /**
     * 向量
     */
    private static String       iv  = "cnambition654321";

    public static void main(String[] args) throws Exception {
        String str = "192.168.1.127##0##15";

        String enCryptstring = enCrypt(str);

        //加密过的二进制数组转化成16进制的字符串  
        // encrytStr = parseByte2HexStr(byteRe);
        System.out.println("加密前：" + str);
        System.out.println("加密后：" + enCryptstring);

        //加密过的16进制的字符串转化成二进制数组  
        // encrytByte = parseHexStr2Byte(encrytStr);
        System.out.println("解密后：" + deCrypt(enCryptstring));
    }

    /** 
     * 加密函数
     * @param text 需要加密的字符串
     * @return String 加密后的字符串
     * @throws Exception
     */
    public static String enCrypt(String text) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(128, new SecureRandom(key.getBytes()));
        SecretKey desKey = keygen.generateKey();
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, desKey, ivSpec);
        byte[] cByte = c.doFinal(text.getBytes("UTF-8"));
        return new String(Base64.encodeBase64URLSafe(cByte));
    }

    /** 
     * 
     *解密函数 
     * @param text 需要解密的字符串
     * @return String 解密后的字符串
     * @throws Exception
     */
    public static String deCrypt(String text) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(128, new SecureRandom(key.getBytes()));
        SecretKey desKey = keygen.generateKey();
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, desKey, ivSpec);
        byte[] base64Byte = Base64.decodeBase64(text.getBytes());
        byte[] cByte = c.doFinal(base64Byte);
        return new String(cByte, "UTF-8");
    }
}
