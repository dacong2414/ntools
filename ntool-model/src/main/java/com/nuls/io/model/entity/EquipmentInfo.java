package com.nuls.io.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 统计客户端是用pc登录，iso ，android登录
 *
 * @author ambition
 * @version $Id: EquipmentInfo.java, v 0.1 2018年1月29日 下午2:05:54 ambition Exp $
 */
@Entity
@Table(name = "equipment_info", schema = "ambitionj2c", catalog = "")
public class EquipmentInfo {
    /*recId ：主键id
    equipmentSysName; 设备系统名称  pc ios android
    equipmentId; mac地址(或者唯一码)
    equipmentName; 设备名称 华为 iphone
    versionInfo  客户端版本信息  */
    @Id
    @Column(name = "rec_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   recId;
    @Basic
    @Column(name = "equipment_sys_name", nullable = true, length = 255)
    private String equipmentSysName;
    @Basic
    @Column(name = "equipment_id", nullable = true, length = 255)
    private String equipmentId;
    @Basic
    @Column(name = "equipment_name", nullable = true, length = 255)
    private String equipmentName;
    @Basic
    @Column(name = "version_info", nullable = true, length = 255)
    private String versionInfo;

    /**
     * Getter method for property <tt>recId</tt>.
     * 
     * @return property value of recId
     */
    public Long getRecId() {
        return recId;
    }

    /**
     * Setter method for property <tt>recId</tt>.
     * 
     * @param recId value to be assigned to property recId
     */
    public void setRecId(Long recId) {
        this.recId = recId;
    }

    /**
     * Getter method for property <tt>equipmentSysName</tt>.
     * 
     * @return property value of equipmentSysName
     */
    public String getEquipmentSysName() {
        return equipmentSysName;
    }

    /**
     * Setter method for property <tt>equipmentSysName</tt>.
     * 
     * @param equipmentSysName value to be assigned to property equipmentSysName
     */
    public void setEquipmentSysName(String equipmentSysName) {
        this.equipmentSysName = equipmentSysName;
    }

    /**
     * Getter method for property <tt>equipmentId</tt>.
     * 
     * @return property value of equipmentId
     */
    public String getEquipmentId() {
        return equipmentId;
    }

    /**
     * Setter method for property <tt>equipmentId</tt>.
     * 
     * @param equipmentId value to be assigned to property equipmentId
     */
    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * Getter method for property <tt>equipmentName</tt>.
     * 
     * @return property value of equipmentName
     */
    public String getEquipmentName() {
        return equipmentName;
    }

    /**
     * Setter method for property <tt>equipmentName</tt>.
     * 
     * @param equipmentName value to be assigned to property equipmentName
     */
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    /**
     * Getter method for property <tt>versionInfo</tt>.
     * 
     * @return property value of versionInfo
     */
    public String getVersionInfo() {
        return versionInfo;
    }

    /**
     * Setter method for property <tt>versionInfo</tt>.
     * 
     * @param versionInfo value to be assigned to property versionInfo
     */
    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

}
