/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.model.common;

/**
 * 通用属性接口
 * @author hhu
 * @version $Id: IGlobalConstants.java, v 0.1 2017年5月23日 上午11:14:31 hhu Exp $
 */
public interface IGlobalConstant {
    /**
     * 默认cache key
     */
    String  DEFAULT_CACHE_NAME            = "DEFAULT_CACHE";
    /**
     * 禁用（value=0）
     */
    Long    DISABLED                      = 0L;
    /**
     * 启用（value=1）
     */
    Long    ENABLED                       = 1L;
    /**
     *  未选中状态
     */
    Long    UNSELECTED                    = 0L;
    /**
     *  选中状态
     */
    Long    SELECTED                      = 1L;
    /**
     *  用户类型：不是超级管理员
     */
    Long    USER_TYPE_NOT_SUPER_ADMIN     = 0L;
    /**
     *  用户类型：超级管理员
     */
    Long    USER_TYPE_SUPER_ADMIN         = 1L;
    /**
     *  角色类型:不是管理员
     */
    Long    ROLE_TYPE_NOT_ISSYS           = 0L;
    /**
     *  角色类型：管理员
     */
    Long    ROLE_TYPE_ISSYS               = 1L;
    /**
     *  系统默认资源:不可以更改
     */
    Long    SYS_FLAG_DEFAULT              = 0L;
    /**
     * 系统默认资源:可以更改
     */
    Long    SYS_FLAG_NOT_DEFAULT          = 1L;
    /**
     *  会诊操作类型：加入会话
     */
    Long    CONSULT_OPERATION_TYPE_ADD    = 1L;
    /**
     *  会诊操作类型：离开会话
     */
    Long    CONSULT_OPERATION_TYPE_LEAVE  = 2L;
    /**
     *  会诊操作类型：异常中断
     */
    Long    CONSULT_OPERATION_TYPE_ABORT  = 3L;
    /**
     *  会诊操作类型：断线重连
     */
    Long    CONSULT_OPERATION_TYPE_BROKEN = 4L;
    /**
     * 当前登录用户（value=currentUser）
     */
    String  CURRENT_USER                  = "currentUser";
    /**
     * 当前域信息（value=currentField）
     */
    String  CURRENT_FIELD                 = "currentField";
    /**
     * 当前用户访问url列表（value=currentUrlList）
     */
    String  CURRENT_URL_LIST              = "currentUrlList";
    /**
     * 同一个请求重复提交时间间隔
     */
    Long    CURRENT_URL_TIME_INTERVAL     = 2000L;
    /**
     * j2c接口的请求失效时间
     */
    Long    SEND_MESSAGE_TIME_INTERVAL    = 600000000 * 1000L;
    /**
     * controller日志（value=0）
     */
    Long    CONTROLLER_LOG                = 0L;
    /**
     * service日志（value=1）
     */
    Long    SERVICE_LOG                   = 1L;
    /**
     * 管理员
     */
    Long    ADMIN_USER                    = 1L;
    /**
     * 管理员名
     */
    String  ADMIN_USER_NAME               = "admin";
    /**
     * 所有用户
     */
    String  ALL_USERS                     = "allUsers";
    /**
     * 不进行分页
     */
    Integer NO_PAGE_QUERY                 = -1;
    /**
     * 分页大小
     */
    Integer DEFAULT_PAGE_SIZE             = 4;
    /**
     * 分页的起始页数
     */
    int     START_PAGE                    = 0;

    /**
     * 框架字段，无CS_ID时
     */
    Long    NO_CSID                       = 0L;
    /**
     * 精确查找
     */
    Long    QUERYTYPE_EXACT               = 1L;
    /**
     * 模糊查找
     */
    Long    QUERYTYPE_FUZZY               = 2L;
    /**
     * 精确日期查找
     */
    Long    QUERYTYPE_DATE                = 4L;
    /**
     * 数字范围查找
     */
    Long    QUERYTYPE_NUMRANGE            = 3L;
    /**
     * 时间范围查找
     */
    Long    QUERYTYPE_TIMERANGE           = 5L;
    /**
     * 下拉列表查询，从常量表取值
     */
    Long    QUERYTYPE_LIST_CONS           = 6L;
    /**
     * 权限分隔符
     */
    String  RESOURCES_SEPARATOR           = "|";
}
