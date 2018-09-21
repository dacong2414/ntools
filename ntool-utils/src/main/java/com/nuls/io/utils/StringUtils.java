/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.nuls.io.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 设置返回提醒消息
     *
     * @param httpServletResponse
     * @param msg 消息正文
     * @param type 0.成功 1.失败
     */
    public static void setAlertMsg(HttpServletResponse response, String msg, int type) {
        //返回页面的权限提示信息
        try {
            String reString = URLEncoder.encode(new Date().getTime() + "##" + type + "##" + msg,
                "UTF-8");
            Cookie cookie = new Cookie("alertMsg", reString);
            cookie.setPath("/");//request.getServletContext().getContextPath()
            cookie.setMaxAge(1);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(outPutException(e));
        }
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
     * 匹配url（spring security中使用）
     *
     * @param source 源字符串   
     * @param target 目标字符串
     * @return boolean 返回匹配结果
     */
    public static boolean match(String source, String target) {
        boolean flag = false;
        //?转成&，避免正则中匹配，*转成.*匹配任意字符，前后加上^$
        //没有*号即为^source$必须得完全匹配才行
        source = "^" + source.replace("?", "&").replace("*", ".*") + "$";
        //?转成&，避免正则中匹配
        target = target.replace("?", "&");
        flag = target.matches(source);
        return flag;
    }

    /**
     * 读邮件模板 
     *
     * @param templatePath 相对于mailTemplate文件夹的路径
     * @return String 模板中的字符串 
     */
    public static String getMailtemplate(String templatePath) {
        String ret = null;
        try {
            String url = StringUtils.class.getResource("").getPath().replaceAll("%20", " ");
            String path = url.substring(0, url.indexOf("WEB-INF")) + "mailTemplate/" + templatePath;
            //jar包中的class文件调用这个接口时返回的path以"file:"开头，这里需调用外部的资源文件，所以需要删除file:
            if (path.startsWith("file")) {
                path = path.substring(5);
            }
            //读取模板文件
            FileInputStream fileInputStream = new FileInputStream(path);
            int length = fileInputStream.available();
            byte bytes[] = new byte[length];
            fileInputStream.read(bytes);
            fileInputStream.close();
            ret = new String(bytes, "gbk");
        } catch (Exception e) {
            logger.error("读取邮件模板失败：" + outPutException(e));
        }
        return ret;
    }

    /**
    * 获取正确 的手机号码
    *
    * @param phoneNos 手机号
    * @return String 手机号
    */
    public static String getRightPhoneNos(String phoneNos) {
        String ret = "";
        if (!isEmpty(phoneNos)) {
            List<String> rpn = new ArrayList<String>();
            String[] hms = phoneNos.split(",");
            for (String no : hms) {
                if (no.trim().length() == 11) {
                    rpn.add(no.trim());
                }
            }
            for (String string : rpn) {
                ret += string + ",";
            }
        }
        if (!isEmpty(ret)) {
            ret = ret.substring(0, ret.length() - 1);
        }
        return ret;
    }

    /**
     * 把用ISO8859_1编码的字符串转成  用GBK编码的字符串
     *
     * @param iso 字符串
     * @return  String gbk   字符串
     */
    public static String iso2gbk(String iso) {
        String gbk = iso;
        try {
            gbk = new String(iso.getBytes("ISO8859_1"), "GBK");
        } catch (Exception e) {
            logger.error(StringUtils.outPutException(e));
        }
        return gbk;
    }

    /**
     * 使用UTF-8编码机制对 application/x-www-form-urlencoded 字符串解码
     *
     * @param iso 要解码的字符串
     * @return String 解码后的字符串
     */
    public static String ajxjUTF8(String iso) {
        String gbk = iso;
        try {
            gbk = URLDecoder.decode(iso, "UTF-8");
        } catch (Exception e) {
            logger.error(StringUtils.outPutException(e));
        }
        return gbk;
    }

    /**
     * 判断是否为null或者"" 为空返回true
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        boolean flag = false;
        if (str == null || "".equals(str)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 将null转为""
     *
     * @param str null
     * @return String ""
     */
    public static String cgStr(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    /**
     * ajax输出封装
     *
     * @param response 响应
     * @param outStr 要输出的字符串
     * @throws IOException
     */
    public static void ajaxOutput(HttpServletResponse response, String outStr) throws IOException {
        response.setContentType("text/html;charset=GBK");
        response.getWriter().write(outStr);
        response.getWriter().flush();
    }

    /**
     * 打印异常的堆栈信息
     *
     * @param e 异常
     * @return String 异常信息
     */
    public static String outPutException(Exception e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new PrintWriter(buf, true));
        String expMessage = buf.toString();
        return expMessage;
    }

    /**
     * 打印异常的堆栈信息
     *
     * @param e 异常
     * @return String 异常信息
     */
    public static String outPutException(Throwable e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new PrintWriter(buf, true));
        String expMessage = buf.toString();
        return expMessage;
    }

    /**
     * 将给定的（##[a-z0-9_]*##）正则表达式编译到模式中（区分大小写匹配）
     *
     * @param str 传入的字符串
     * @return List<?> 返回匹配的list（去重后的）
     */
    public static List<?> myRegex(String str) {
        //Pattern.CASE_INSENSITIVE使不区分大小写匹配
        Pattern pattern = Pattern.compile("##[a-z0-9_]*##", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return removeDuplicateWithOrder(list);
    }

    /**
     * List去重
     *
     * @param list 需要去重的list
     * @return List<Object> 去重后的list
     */
    public static List<Object> removeDuplicateWithOrder(List<?> list) {
        Set<Object> set = new HashSet<Object>();
        List<Object> newList = new ArrayList<Object>();
        for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    /**
     * 
     *判断字符串是否是数字 是数字返回true
     * @param str 传入字符串
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }

        if (str.matches("\\d*")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 在日志中直接写@+人名，获取分享的人名
     *
     * @param content 日志内容
     * @return List<String> 要@的姓名
     */
    public static List<String> findAtLog(String content) {
        //正则表达式匹配格式
        Pattern pattern = Pattern.compile("@{1}\\D{2,4}\\s");
        Matcher matcher = pattern.matcher(content);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            //得到content中@### 的字符串
            String showName = matcher.group();
            //从showName的第一位截取，即去掉@符号和空格，得到人名
            String name = showName.substring(1).trim();
            list.add(name);
        }
        return list;
    }

    /**
     * 
     *将set集合转化为String字符串
     * @param set 传入的set
     * @return String 转化后的字符串
     */
    public static <T> String setToStr(Set<T> set) {
        String ret = "";
        for (Object obj : set) {
            String string = obj.toString();
            ret = ret + string + ",";
        }
        if (ret.length() > 0) {
            ret = ret.substring(0, ret.length() - 1);
        }
        return ret;
    }

    /**
     * AES加密
     *
     * @param msg 要加密的字符串
     * @return String 加密后的字符串
     */
    public static String encrypt(String msg) {
        String ret = null;
        try {
            if (msg != null) {
                ret = AesUtils.encrypt(msg);
            }
        } catch (Exception e) {
            logger.error(StringUtils.outPutException(e));
        }//加解密字符串
        return ret;
    }

    /**
     * AES解密
     *
     * @param msg 要解密的字符串
     * @return String 解密后的字符串
     */
    public static String decrypt(String msg) {
        String ret = null;
        try {
            if (msg != null) {
                ret = AesUtils.decrypt(msg);
            }
        } catch (Exception e) {
            logger.error(StringUtils.outPutException(e));
        }//加解密字符串
        return ret;
    }

    /**
     * 获取WEB-INF/config/+source中指定的properties的属性值
     * 如：WEB-INF/config/hibernate/hibernate.properties的dataSource.password值的调用方式
     * getWeixinPropByName("dataSource.password", "/hibernate/hibernate.properties")
     * @param propName 属性的名称（"dataSource.password"）
     * @param source 资源地址（"/hibernate/hibernate.properties"）
     * @return String 返回属性名称对应的value
     */
    public static String getPropByName(String propName, String source) {
        try {
            String url = StringUtils.class.getResource("").getPath().replaceAll("%20", " ");
            String path = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/config" + source;
            //jar包中的class文件调用这个接口时返回的path以"file:"开头，这里需调用外部的资源文件，所以需要删除file:
            if (path.startsWith("file")) {
                path = path.substring(5);
            }
            Properties config = new Properties();
            config.load(new FileInputStream(path));
            return config.getProperty(propName);
        } catch (Exception e) {
            logger.error("读取配置文件失败：" + outPutException(e));
        }
        return null;
    }

    /**
     * HTML标签转义方法 —— java代码库
     * @param content 要转义的字符串
     * @return String 转义后的字符串
     */
    public static String html(String content) {
        if (content == null)
            return "";
        String html = content;
        html = html.replaceAll("'", "&apos;");
        html = html.replaceAll("\"", "&quot;");
        html = html.replaceAll("\t", "&nbsp;&nbsp;");// 替换跳格
        html = html.replaceAll(" ", "&nbsp;");// 替换空格
        html = html.replaceAll("<", "&lt;");
        html = html.replaceAll(">", "&gt;");
        html = html.replaceAll("\n", "<br/>");
        return html;
    }

    /**
     * 数据列表中转译特殊字符
     *
     * @param content
     * @return
     */
    public static String sqlHtml(Object content) {
        if (content == null)
            return "";
        String html = content.toString();
        if (content instanceof String) {
            html = html.replaceAll("'", "&apos;");
            html = html.replaceAll("\"", "&quot;");
            html = html.replaceAll("\t", "&nbsp;&nbsp;");// 替换跳格
            html = html.replaceAll("<", "&lt;");
            html = html.replaceAll(">", "&gt;");
            html = html.replaceAll("\n", "<br/>");
        }
        return html;
    }

    /** 
     * 手机号验证 
     *  
     * @param  str 字符串
     * @return boolean 验证通过返回true 
     */
    public static boolean isMobile(String str) {
        if (str != null) {
            Pattern p = null;
            Matcher m = null;
            boolean b = false;
            p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
            m = p.matcher(str);
            b = m.matches();
            return b;
        } else {
            return false;
        }
    }

    /**
     * 验证输入的邮箱格式是否符合  true 合法
     * @param email 邮箱字符串
     * @return boolean 合法返回true
     */
    public static boolean isEmail(String email) {
        if (email != null) {
            Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
            Matcher m = p.matcher(email);
            return m.find();
        } else {
            return false;
        }
    }

    /**
     * 
     * 在范围内返回true，否则返回false
     * @param str 要验证的字符串
     * @param start 范围开始值
     * @param end 范围结束值
     * @return
     */
    public static boolean lengthScope(String str, int start, int end) {
        boolean flag = false;
        if (str == null || start >= end) {
            return flag;
        }
        if (str.length() > end || str.length() < start) {
            return flag;
        }
        flag = true;
        return flag;
    }

    /**
     * 
     * 检查是否存在防止SQL注入
     * @param str 检查字符串
     * @return true表示存在SQL注入
     */
    public static boolean sql_inj(String str) {
        if (!isEmpty(str)) {
            String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|,";
            //这里的东西还可以自己添加
            String[] inj_stra = inj_str.split("\\|");
            for (int i = 0; i < inj_stra.length; i++) {
                if (str.indexOf(inj_stra[i]) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getLocalMac(InetAddress ia) throws SocketException {
        //获取网卡，获取地址
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString().toUpperCase();
    }
}
