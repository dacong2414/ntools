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
 * 系统日志
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "sys_log", schema = "ambitionj2c", catalog = "")
public class SysLog {
    private Long   recId;
    private String logDesc;
    private Long   logType;
    private String exceptionCode;
    private String exceptionDetail;
    private String requestIp;
    private String method;
    private String params;
    private Long   operatorId;
    private Date   operateDate;

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
    @Column(name = "log_desc", nullable = false, length = 255)
    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
    }

    @Basic
    @Column(name = "log_type", nullable = false)
    public Long getLogType() {
        return logType;
    }

    public void setLogType(Long logType) {
        this.logType = logType;
    }

    @Basic
    @Column(name = "exception_code", nullable = false, length = 255)
    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    @Basic
    @Column(name = "exception_detail", nullable = false, length = 255)
    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    @Basic
    @Column(name = "request_ip", nullable = false, length = 255)
    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    @Basic
    @Column(name = "method", nullable = false, length = 255)
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "params", nullable = false, length = 255)
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Basic
    @Column(name = "operator_id", nullable = false)
    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "operate_date", nullable = true)
    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SysLog sysLog = (SysLog) o;

        if (recId != sysLog.recId)
            return false;
        if (logType != sysLog.logType)
            return false;
        if (operatorId != sysLog.operatorId)
            return false;
        if (logDesc != null ? !logDesc.equals(sysLog.logDesc) : sysLog.logDesc != null)
            return false;
        if (exceptionCode != null ? !exceptionCode.equals(sysLog.exceptionCode)
            : sysLog.exceptionCode != null)
            return false;
        if (exceptionDetail != null ? !exceptionDetail.equals(sysLog.exceptionDetail)
            : sysLog.exceptionDetail != null)
            return false;
        if (requestIp != null ? !requestIp.equals(sysLog.requestIp) : sysLog.requestIp != null)
            return false;
        if (method != null ? !method.equals(sysLog.method) : sysLog.method != null)
            return false;
        if (params != null ? !params.equals(sysLog.params) : sysLog.params != null)
            return false;
        if (operateDate != null ? !operateDate.equals(sysLog.operateDate)
            : sysLog.operateDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (logDesc != null ? logDesc.hashCode() : 0);
        result = 31 * result + (int) (logType ^ (logType >>> 32));
        result = 31 * result + (exceptionCode != null ? exceptionCode.hashCode() : 0);
        result = 31 * result + (exceptionDetail != null ? exceptionDetail.hashCode() : 0);
        result = 31 * result + (requestIp != null ? requestIp.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (int) (operatorId ^ (operatorId >>> 32));
        result = 31 * result + (operateDate != null ? operateDate.hashCode() : 0);
        return result;
    }
}
