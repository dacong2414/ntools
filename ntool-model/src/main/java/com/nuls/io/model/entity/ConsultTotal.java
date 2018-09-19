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
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "consult_total", schema = "ambitionj2c", catalog = "")
public class ConsultTotal {
    private Long   recId;
    private Long   uId;
    private String billNum;
    private Date   gmtBegin;
    private Date   gmtEnd;

    /* `rec_id`：主键ID
     `u_id`：发起人ID
     `bill_num`：单据号
     'gmt_begin':会诊开始时间
     'gmt_end':会诊结束时间
    */
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
    @Column(name = "u_id", nullable = false)
    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    @Basic
    @Column(name = "bill_num", nullable = false, length = 2000)
    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    @Basic
    @Column(name = "gmt_begin", nullable = false)
    public Date getGmtBegin() {
        return gmtBegin;
    }

    public void setGmtBegin(Date gmtBegin) {
        this.gmtBegin = gmtBegin;
    }

    @Basic
    @Column(name = "gmt_end", nullable = true)
    public Date getGmtEnd() {
        return gmtEnd;
    }

    public void setGmtEnd(Date gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ConsultTotal that = (ConsultTotal) o;

        if (recId != that.recId)
            return false;
        if (uId != that.uId)
            return false;
        if (billNum != null ? !billNum.equals(that.billNum) : that.billNum != null)
            return false;
        if (gmtBegin != null ? !gmtBegin.equals(that.gmtBegin) : that.gmtBegin != null)
            return false;
        if (gmtEnd != null ? !gmtEnd.equals(that.gmtEnd) : that.gmtEnd != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (int) (uId ^ (uId >>> 32));
        result = 31 * result + (billNum != null ? billNum.hashCode() : 0);
        result = 31 * result + (gmtBegin != null ? gmtBegin.hashCode() : 0);
        result = 31 * result + (gmtEnd != null ? gmtEnd.hashCode() : 0);
        return result;
    }
}
