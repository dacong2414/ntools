/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ambition
 * @version $Id: AccessKeyInfo.java, v 0.1 2017年9月6日 下午4:59:17 ambition Exp $
 */
@Entity
@Table(name = "access_key_info", schema = "ambitionj2c", catalog = "")
public class AccessKeyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Long   recId;
    @Basic
    @Column(name = "access_key_id")
    private String accessKeyId;     //accessKeyID密匙id
    @Basic
    @Column(name = "access_key_secret")
    //得到加密的密匙
    private String accessKeySecret;

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
