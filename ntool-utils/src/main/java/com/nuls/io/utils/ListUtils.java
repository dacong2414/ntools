/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */
package com.nuls.io.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *list处理类
 * @author abj
 * @version $Id: ListUtils.java, v 0.1 2012-2-13 上午11:56:45 abj Exp $
 */
public class ListUtils {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ListUtils.class);

    /**
     * 判断List是否为null或者空
     * @param list list集合
     * @return boolean  为null/空 返回true 否则返回false
     */
    public static boolean isEmpty(List<?> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 取list的第一个元素，没有返回null
     *
     * @param list list集合
     * @return Object 对象
     */
    public static Object retList(List<?> list) {
        if (!isEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 深度复制对象
     *
     * @param object 要复制的对象
     * @return Object 复制后的对象
     */
    public static Object deepClone(Object object) {
        Object object2 = new Object();
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(object);
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            object2 = oi.readObject();
        } catch (Exception e) {
            logger.error(StringUtils.outPutException(e));
        }
        return object2;
    }

    /**
     * 将origin中的值塞入target中
     *
     * @param origin
     * @param target
     */
    public static void add(String[] origin, Set<String> target) {
        if (target != null && origin != null) {
            for (String object : origin) {
                target.add(object);
            }
        }
    }
}
