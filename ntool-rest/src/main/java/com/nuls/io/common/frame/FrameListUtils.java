/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.nuls.io.common.frame;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuls.io.utils.StringUtils;

/**
 * 框架列表工具类
 *
 * @author zhangxi
 * @version $Id: FrameListUtils.java, v 0.1 2017年7月18日 下午4:52:11 zhangxi Exp $
 */
public class FrameListUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FrameListUtils.class);

    /**
     * 根据frameName从xml中读取sql查询主体信息
     * @param frameName 框架列表名称
     * @return
     */
    public static SqlQueryInfo getQueryBySqlId(String frameName) {
        SqlQueryInfo sqlQueryInfo = new SqlQueryInfo();
        ClassLoader classLoader = FrameListUtils.class.getClassLoader();
        String url = classLoader.getResource("frame/" + frameName + ".xml").getPath();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(new File(url));
            Element element = doc.getRootElement();
            sqlQueryInfo.setFrameName(frameName);
            sqlQueryInfo.setSqlTitle(element.getChild("sqlTitle").getTextTrim());
            sqlQueryInfo.setSqlTemp(element.getChild("sqlTemp").getTextTrim());
            sqlQueryInfo.setExtendWidth(element.getChild("extendWidth").getTextTrim());
            sqlQueryInfo.setSqlDetial(element.getChild("sqlDetial").getTextNormalize());
            Element extendList = element.getChild("extendList");
            Element fieldList = element.getChild("fieldList");
            //加载字段属性信息
            if (fieldList != null) {
                List<SqlFieldInfo> fieldInfos = new ArrayList<SqlFieldInfo>();
                for (Element field : fieldList.getChildren()) {
                    SqlFieldInfo fieldInfo = new SqlFieldInfo();
                    fieldInfo.setFrameName(frameName);
                    fieldInfo.setFieldName(field.getChild("fieldName").getTextNormalize());
                    fieldInfo.setShowName(field.getChild("showName").getTextNormalize());
                    fieldInfo.setShowIndex(cgStrToLong(field.getChild("showIndex")));
                    fieldInfo.setFieldType(field.getChild("fieldType").getTextNormalize());
                    fieldInfo.setQueryType(cgStrToLong(field.getChild("queryType")));
                    fieldInfo.setCsId(cgStrToLong(field.getChild("csId")));
                    fieldInfo.setConstantInfos(getSqlConstantInfo(cgStrToLong(field
                        .getChild("csId"))));
                    fieldInfos.add(fieldInfo);
                }
                //按照showIndex排序
                Collections.sort(fieldInfos, new Comparator<SqlFieldInfo>() {
                    public int compare(SqlFieldInfo o1, SqlFieldInfo o2) {
                        return o1.getShowIndex().intValue() - o2.getShowIndex().intValue();
                    }
                });
                sqlQueryInfo.setFieldInfos(fieldInfos);
            }
        } catch (Exception e) {
            LOG.error("获取sql查询主体信息出错：" + StringUtils.outPutException(e));
        }
        return sqlQueryInfo;
    }

    /**
     * 将Element转化为Long类型，null值转化为0
     *
     * @param field
     * @return
     */
    public static Long cgStrToLong(Element field) {
        Long ret = 0L;
        if (field != null) {
            ret = Long
                .valueOf(field.getTextNormalize().equals("") ? "0" : field.getTextNormalize());
        }
        return ret;
    }

    /**
     * 从xml中读取常量表信息
     *
     * @return
     */
    public static List<SqlConstantInfo> getSqlConstantInfo() {
        ClassLoader classLoader = FrameListUtils.class.getClassLoader();
        String url = classLoader.getResource("frame/constants.xml").getPath();
        SAXBuilder builder = new SAXBuilder();
        List<SqlConstantInfo> constantInfos = new ArrayList<>();
        try {
            Document doc = builder.build(new File(url));
            Element element = doc.getRootElement();
            if (element != null) {
                for (Element constantInfo : element.getChildren()) {
                    Element pointList = constantInfo.getChild("pointList");
                    for (Element point : pointList.getChildren()) {
                        SqlConstantInfo sqlConstantInfo = new SqlConstantInfo();
                        sqlConstantInfo.setCsId(cgStrToLong(constantInfo.getChild("csId")));
                        sqlConstantInfo.setCsName(point.getChild("csName").getTextNormalize());
                        sqlConstantInfo.setCsValue(point.getChild("csValue").getTextNormalize());
                        constantInfos.add(sqlConstantInfo);
                    }
                }
            }

        } catch (Exception e) {
            LOG.error("获取sql常量表出错：" + StringUtils.outPutException(e));
        }
        return constantInfos;
    }

    public static void setFlag(String flag) {
        ClassLoader classLoader = FrameListUtils.class.getClassLoader();
        String url = classLoader.getResource("flag/flag.xml").getPath();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(new File(url));
            Element element = doc.getRootElement();
            if (element != null) {
                element.setText(flag);
            }
            XMLOutputter output = new XMLOutputter();
            output.output(doc, new FileOutputStream(url));
        } catch (Exception e) {
            LOG.error("设置分组可见配flag：" + StringUtils.outPutException(e));
        }
    }

    public static String getFlag() {
        ClassLoader classLoader = FrameListUtils.class.getClassLoader();
        String url = classLoader.getResource("flag/flag.xml").getPath();
        SAXBuilder builder = new SAXBuilder();
        String flag = "";
        try {
            Document doc = builder.build(new File(url));
            Element element = doc.getRootElement();
            if (element != null) {
                flag = element.getTextNormalize();
            }
        } catch (Exception e) {
            LOG.error("获取分组可见配flag：" + StringUtils.outPutException(e));
        }
        return flag;
    }

    /**
     * 根据CsId获取SqlConstantInfo
     *
     * @param csId
     * @return
     */
    public static List<SqlConstantInfo> getSqlConstantInfo(Long csId) {
        List<SqlConstantInfo> retConstantInfos = new ArrayList<SqlConstantInfo>();
        List<SqlConstantInfo> constantInfos = getSqlConstantInfo();
        for (SqlConstantInfo constantInfo : constantInfos) {
            if (constantInfo.getCsId().equals(csId)) {
                retConstantInfos.add(constantInfo);
            }
        }
        return retConstantInfos;
    }

}
