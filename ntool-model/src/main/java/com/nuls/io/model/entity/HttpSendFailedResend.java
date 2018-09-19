/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author ambition
 * @version $Id: AccessKeyInfo.java, v 0.1 2017年9月6日 下午4:59:17 ambition Exp $
 */
@Entity
@Data
@DynamicUpdate
@Table(name = "http_send_failed_resend", schema = "ambitionj2c", catalog = "")
public class HttpSendFailedResend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Long             recId;
    @Basic
    @Column(name = "data")
    private String           data;
    @Basic
    @Column(name = "flag")
    private Long             flag;
    @Basic
    @Column(name = "gmt_create", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date             gmtCreate;
    /**
     * 发送失败 1L
     */
    public static final Long FAIL    = 1L;
    /**
     * 发送成功 0L
     */
    public static final Long SUCCESS = 0L;

}
