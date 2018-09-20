package com.nuls.io.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.nuls.io.utils.StringUtils;


@Component
public class DateConverter implements Converter<String, Date> {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);

    /**
     * 将String转成yyyy-MM-dd HH:mm:ss 的Date
     * @param source 传入的时间字符串
     * @return Date 返回格式化的Date
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public Date convert(String source) {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat1.setLenient(false);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat2.setLenient(false);
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH");
        dateFormat3.setLenient(false);
        SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat4.setLenient(false);
        Date date = null;
        try {
            if (!StringUtils.isEmpty(source)) {
                if (source.length() == 19) {
                    date = dateFormat1.parse(source);
                } else if (source.length() == 16) {
                    date = dateFormat2.parse(source);
                } else if (source.length() == 13) {
                    date = dateFormat3.parse(source);
                } else if (source.length() == 10) {
                    date = dateFormat4.parse(source);
                }
            }
        } catch (ParseException e) {
            logger.error(StringUtils.outPutException(e));
        }
        return date;
    }
}