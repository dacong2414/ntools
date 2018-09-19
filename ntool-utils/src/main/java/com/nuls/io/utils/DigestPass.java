package com.nuls.io.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加密
 *
 * @author chaoyuhang
 * @version $Id: DigestPass.java, v 0.1 2012-2-23 下午04:53:54 chaoyuhang Exp $
 */
public class DigestPass {
    /**
     * 日志
     */
    private static final Logger  logger = LoggerFactory.getLogger(DigestPass.class);

    private static MessageDigest messageDigest;
    private static String        result = "";
    private static byte[]        args   = null;

    /**
     * 获取MD5加密
     *
     * @param userpass 要加密的字符串
     * @return String 加密后的字符串
     */
    public static String getDigestPassWord(String userpass) {
        try {
            userpass = userpass + "cqabj2012_MD5";
            // 生成MessageDigest对象,传入所用算法的参数(MD5)   
            messageDigest = MessageDigest.getInstance("MD5");
            // 使用 getBytes( )方法生成字符串数组   
            messageDigest.update(userpass.getBytes("GBK"));
            // 执行MessageDigest对象的digest( )方法完成计算，计算的结果通过字节类型的数组返回   
            args = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error(StringUtils.outPutException(e));
        } catch (UnsupportedEncodingException ee) {
            logger.error(StringUtils.outPutException(ee));
        } finally {
            messageDigest.reset();
        }

        // 将结果转换成字符串   
        result = "";// result清空，否则它会自动累加！！！   
        for (int i = 0; i < args.length; i++) {
            result += Integer.toHexString((0x000000ff & args[i]) | 0xffffff00).substring(6);
        }
        return result;
    }
}