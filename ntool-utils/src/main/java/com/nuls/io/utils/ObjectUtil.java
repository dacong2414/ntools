package com.nuls.io.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 对象操作类
 * Created by hhu on 2017/7/28.
 */
public class ObjectUtil {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);
    /**
     *
     * 对象类型强制转换
     * @param obj Object对象
     * @param <T> 泛型强制转换
     * @return null 或者 具体的对象
     * @throws ClassCastException 会发生类型转换异常
     */
    public static <T> T typeConversion(Object obj) throws ClassCastException {
        return obj == null ? null : (T) obj;
    }

    
    /**
     * 获取流名
     * @return
     */
    public static String makeStreamName() {
        return digits(UUID.randomUUID().getMostSignificantBits(), 12);
    }

    /**
     * 获取fcode
     * @return
     */
    public static String makeFCode() {
        return digits(UUID.randomUUID().getLeastSignificantBits(), 12);
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    /**
     * 生成6位随机数
     * @return
     */
    public static Integer makeRandomCode() {
        Random random = new Random();
        int min = 100000, max = 899999;
        int randNum = min + random.nextInt(max);
        logger.debug("生成6位随机数：{}", randNum);
        return randNum;
    }
}
