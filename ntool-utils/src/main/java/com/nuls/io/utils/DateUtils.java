package com.nuls.io.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *  Date相关处理
 *
 * @author ambition
 * @version $Id: DateUtils.java, v 0.1 2016年5月26日 上午11:49:06 ambition Exp $
 */

public class DateUtils {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String  FULL_DATE_PATTEN   = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String  DATE_MINUTE_PATTEN = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd
     */
    public static final String  DAY_DATE_PATTEN    = "yyyy-MM-dd";

    /**
     * 把Date转成String
     *
     * @param date 传入Date类型的时间
     * @param patten 传入的格式如（"yyyy-MM-dd HH:mm:ss"）
     * @return String
     */
    public static String changeDateToStr(Date date, String patten) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        String ret = "";
        try {
            if (null != date) {
                ret = simpleDateFormat.format(date);
            }
        } catch (Exception e) {
            logger.error(StringUtils.outPutException(e));
        }

        return ret;
    }

    /**
     * 把String 的时间转成Date类型的时间
     *
     * @param date 传入的时间字符串
     * @param patten 传入的格式如（"yyyy-MM-dd HH:mm:ss"）
     * @return Date
     */

    public static Date changeStrToDate(String date, String patten) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        Date date2 = new Date();
        try {
            if (null != date) {
                date2 = simpleDateFormat.parse(date);
            }
        } catch (ParseException e) {
            logger.error(StringUtils.outPutException(e));
        }

        return date2;
    }

    /**
     * 把String 的时间转成Date类型的时间
     *
     * @param date 传入的时间字符串
     * @param patten 传入的格式如（" String pattern = "EEE MMM dd HH:mm:ss zzz yyyy"; "）
     * @return Date
     */

    public static Date changeStrToDatePatten(String date, String patten) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten, Locale.US);
        Date date2 = new Date();
        try {
            if (null != date) {
                date2 = simpleDateFormat.parse(date);
            }
        } catch (ParseException e) {
            logger.error(StringUtils.outPutException(e));
        }

        return date2;
    }

    /**
     * 把Date转成String
     *
     * @param dateStr 传入Date类型的时间
     * @param patten 传入的格式如（"yyyy-MM-dd HH:mm:ss"）
     * @return String
     */
    public static Date changeStringToDate(String dateStr, String patten) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        Date ret = null;
        if (!StringUtils.isEmpty(dateStr)) {
            ret = simpleDateFormat.parse(dateStr);
        }
        return ret;
    }

}
