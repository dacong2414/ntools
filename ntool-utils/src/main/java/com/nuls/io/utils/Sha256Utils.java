package com.nuls.io.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串加密压缩
 *
 */
public class Sha256Utils {
    private static final Logger LOG = LoggerFactory.getLogger(Sha256Utils.class);

    private Sha256Utils() {
    }

    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc  要加密的字符串
     * @param encName 加密类型
     * @return
     */
    public static String encrypt(String strSrc, String encName) {
        MessageDigest md;
        String strDes;
        String midEncName = encName;
        try {
            if (StringUtils.isEmpty(encName)) {
                midEncName = "SHA-256";
            }
            md = MessageDigest.getInstance(midEncName);
            md.update(strSrc.getBytes());
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
            return null;
        }
        return strDes;
    }

    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc 要加密的字符串
     * @return 加密字符串
     */
    public static String encryptSha256(String strSrc) {
        return encrypt(strSrc, "SHA-256");
    }

    public static String bytes2Hex(byte[] bts) {
        StringBuilder des = new StringBuilder();
        String tmp;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();
    }
}
