package com.nuls.io.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 报告信息
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "report_info", schema = "ambitionj2c", catalog = "")
public class ReportInfo {
    private Long   recId;
    private Date   checkBeginTime;
    private Date   checkEndTime;
    private String checkPosition;
    private Date   gmtCreate;
    private Date   gmtMod;
    private String patientId;
    private String reportId;

    /*`rec_id`：主键id,
    `check_begin_time` ：检查开始时间,
    `check_end_time` ：检查结束时间,
    `check_position`：检查部位,
    `patient_id` ：患者id,
    `report_id` ：报告id,(唯一)
    `gmt_create` ：创建时间,
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
    @Column(name = "gmt_create", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_mod", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getGmtMod() {
        return gmtMod;
    }

    public void setGmtMod(Date gmtMod) {
        this.gmtMod = gmtMod;
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

        ReportInfo that = (ReportInfo) o;

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
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(that.gmtMod) : that.gmtMod != null)
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
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        result = 31 * result + (patientId != null ? patientId.hashCode() : 0);
        result = 31 * result + (reportId != null ? reportId.hashCode() : 0);
        return result;
    }
}
