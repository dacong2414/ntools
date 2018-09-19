package com.nuls.io.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 删除备份表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "delete_back_up", schema = "ambitionj2c", catalog = "")
public class DeleteBackUp {
    private Long   recId;
    private Date   checkBeginTime;
    private Date   checkEndTime;
    private String checkPosition;
    private Date   deleteTime;
    private Date   gmtCreate;
    private Date   gmtModified;
    private String patientId;
    private String reportId;

    /*`rec_id`：主键ID,
    `check_begin_time` ：检查开始时间,
    `check_end_time`：检查结束时间,
    `check_position`：检查位置,
    `patient_id`：病人id,
    `report_id`：报告id,
    `delete_time` ：删除时间,
    `gmt_create` ：创建时间
    `gmt_modified` ：修改时间*/
    @Id
    @Column(name = "rec_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    @Basic
    @Column(name = "check_begin_time", nullable = true)
    public Date getCheckBeginTime() {
        return checkBeginTime;
    }

    public void setCheckBeginTime(Date checkBeginTime) {
        this.checkBeginTime = checkBeginTime;
    }

    @Basic
    @Column(name = "check_end_time", nullable = true)
    public Date getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(Date checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    @Basic
    @Column(name = "check_position", nullable = true, length = 255)
    public String getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(String checkPosition) {
        this.checkPosition = checkPosition;
    }

    @Basic
    @Column(name = "delete_time", nullable = true)
    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Basic
    @Column(name = "gmt_create", nullable = true)
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modified", nullable = true)
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "patient_id", nullable = true, length = 255)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "report_id", nullable = true, length = 255)
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DeleteBackUp that = (DeleteBackUp) o;

        if (recId != that.recId)
            return false;
        if (checkBeginTime != null ? !checkBeginTime.equals(that.checkBeginTime)
            : that.checkBeginTime != null)
            return false;
        if (checkEndTime != null ? !checkEndTime.equals(that.checkEndTime)
            : that.checkEndTime != null)
            return false;
        if (checkPosition != null ? !checkPosition.equals(that.checkPosition)
            : that.checkPosition != null)
            return false;
        if (deleteTime != null ? !deleteTime.equals(that.deleteTime) : that.deleteTime != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
            return false;
        if (gmtModified != null ? !gmtModified.equals(that.gmtModified) : that.gmtModified != null)
            return false;
        if (patientId != null ? !patientId.equals(that.patientId) : that.patientId != null)
            return false;
        if (reportId != null ? !reportId.equals(that.reportId) : that.reportId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (checkBeginTime != null ? checkBeginTime.hashCode() : 0);
        result = 31 * result + (checkEndTime != null ? checkEndTime.hashCode() : 0);
        result = 31 * result + (checkPosition != null ? checkPosition.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
        result = 31 * result + (patientId != null ? patientId.hashCode() : 0);
        result = 31 * result + (reportId != null ? reportId.hashCode() : 0);
        return result;
    }
}
