/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.nuls.io.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author cyh
 * @version $Id: AesUtils.java, v 0.1 2016年10月27日 下午1:57:07 cyh Exp $
 */
public class AesUtils {
    /**
     * 加密/解密密码
     */
    private static String key = "cnambition123456";
    /**
     * 向量长度必须是16字节(128bit)，与数据块一致(AES加密数据块分组长度必须为128比特)
     */
    private static String iv  = "cnambition654321";

    /**
     *加密String明文输入,经过BASE64编码String密文输出 
     * @param text 需要加密的字符串
     * @return String 加密后的字符串
     * @throws Exception
     */
    public static String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        Base64 base64 = new Base64();
        byte[] texts = text.getBytes("gb2312");
        byte[] results = cipher.doFinal(texts);
        //System.err.println("加密后密文长度：" + results.length);
        return new String(base64.encode(results));
    }

    /**
     *解密 以BASE64形式String密文输入,String明文输出 
     *
     * @param text 需要解密的字符串
     * @return String 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        Base64 base64 = new Base64();
        byte[] texts = base64.decode(text.getBytes());
        //System.err.println("解密前密文长度：" + texts.length);
        byte[] results = cipher.doFinal(texts);
        return new String(results, "gb2312");
    }
}
