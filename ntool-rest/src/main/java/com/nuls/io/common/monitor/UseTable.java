package com.nuls.io.common.monitor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.common, v 0.1 2017/11/6 10:47 hhu Exp $$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface UseTable {
    String[] tables();
}