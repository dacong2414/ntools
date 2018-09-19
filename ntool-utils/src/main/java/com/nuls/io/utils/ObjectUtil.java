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
     * 将Obeject对象转换为Long对象
     * @param obj Obeject对象
     * @param entityClazz 类类型对象
     * @return null 或者 具体的对象
     * @throws ClassCastException 类型转换异常
     * @throws ParseException 解析异常
     */
    public static Long objectToLong(Object obj, Class entityClazz) throws ParseException {
        return objectToType(obj, entityClazz);
    }

    /**
     * 将Obeject对象转换为Date对象
     * @param obj Obeject对象
     * @param entityClazz 类类型对象
     * @return null 或者 具体的对象
     * @throws ClassCastException 类型转换异常
     * @throws ParseException 解析异常
     */
    public static Date objectToDate(Object obj, Class entityClazz) throws ParseException {
        return objectToType(obj, entityClazz);
    }

    /**
     * 将Obeject对象转换为类类型对象
     * @param obj Obeject对象
     * @param entityClazz 类类型对象
     * @param <T> 泛型对象
     * @return null 或者 具体的对象
     * @throws ClassCastException 类型转换异常
     * @throws ParseException 解析异常
     */
    public static <T> T objectToType(Object obj, Class entityClazz) throws ParseException {
        if (obj == null) {
            return null;
        }
        String temp = String.valueOf(obj);
        switch (entityClazz.getName()) {
            case "java.lang.String":
                return typeConversion(temp);
            case "java.lang.Long":
                return typeConversion(Long.parseLong(temp));
            case "java.lang.Integer":
                return typeConversion(Integer.parseInt(temp));
            case "java.lang.Double":
                return typeConversion(Double.parseDouble(temp));
            case "java.lang.Boolean":
                return typeConversion(Boolean.parseBoolean(temp));
            case "java.util.Date":
                return typeConversion(DateUtils
                        .changeStringToDate(temp, DateUtils.FULL_DATE_PATTEN));
            default:
                return null;
        }
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
