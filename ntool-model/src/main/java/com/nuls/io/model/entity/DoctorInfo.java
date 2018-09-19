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
 * 医生表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "doctor_info", schema = "ambitionj2c", catalog = "")
public class DoctorInfo {
    private Long   recId;
    private String doctorName;
    private Date   gmtCreate;
    private Date   gmtMod;
    private Long   kindId;
    private String hospitalName;

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
    @Column(name = "doctor_name", nullable = true, length = 255)
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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
    @Column(name = "gmt_mod", nullable = true)
    public Date getGmtMod() {
        return gmtMod;
    }

    public void setGmtMod(Date gmtMod) {
        this.gmtMod = gmtMod;
    }

    @Basic
    @Column(name = "kind_id", nullable = true)
    public Long getKindId() {
        return kindId;
    }

    public void setKindId(Long kindId) {
        this.kindId = kindId;
    }

    @Basic
    @Column(name = "hospital_name", nullable = true, length = 255)
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DoctorInfo that = (DoctorInfo) o;

        if (recId != that.recId)
            return false;
        if (doctorName != null ? !doctorName.equals(that.doctorName) : that.doctorName != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(that.gmtMod) : that.gmtMod != null)
            return false;
        if (kindId != null ? !kindId.equals(that.kindId) : that.kindId != null)
            return false;
        if (hospitalName != null ? !hospitalName.equals(that.hospitalName)
            : that.hospitalName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (doctorName != null ? doctorName.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        result = 31 * result + (kindId != null ? kindId.hashCode() : 0);
        result = 31 * result + (hospitalName != null ? hospitalName.hashCode() : 0);
        return result;
    }
}
