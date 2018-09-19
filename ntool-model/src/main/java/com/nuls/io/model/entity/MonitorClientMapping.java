package com.nuls.io.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 监控映射表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "monitor_client_mapping", schema = "ambitionj2c", catalog = "")
public class MonitorClientMapping {
    private Long recId;
    private Long clientUid;
    private Long monitorUid;

    /*`rec_id`：主键ID,
    `client_uid` ：诊室端的uId（对应user_info的u_id）,
    `monitor_uid` ：监控端uId  （对应user_info的u_id）    
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
    @Column(name = "client_uid", nullable = true)
    public Long getClientUid() {
        return clientUid;
    }

    public void setClientUid(Long clientUid) {
        this.clientUid = clientUid;
    }

    @Basic
    @Column(name = "monitor_uid", nullable = true)
    public Long getMonitorUid() {
        return monitorUid;
    }

    public void setMonitorUid(Long monitorUid) {
        this.monitorUid = monitorUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MonitorClientMapping that = (MonitorClientMapping) o;

        if (recId != that.recId)
            return false;
        if (clientUid != null ? !clientUid.equals(that.clientUid) : that.clientUid != null)
            return false;
        if (monitorUid != null ? !monitorUid.equals(that.monitorUid) : that.monitorUid != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (clientUid != null ? clientUid.hashCode() : 0);
        result = 31 * result + (monitorUid != null ? monitorUid.hashCode() : 0);
        return result;
    }
}
