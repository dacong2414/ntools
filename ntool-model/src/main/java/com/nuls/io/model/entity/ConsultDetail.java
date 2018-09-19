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
 * 会诊详情表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "consult_detail", schema = "ambitionj2c", catalog = "")
public class ConsultDetail {
    private Long             recId;
    private Long             linkId;
    private Long             uId;
    private Long             opra;
    private Long             inviterId;
    private Date             gmtCreate;
    /* `rec_id`：主键ID
     `linkId` ：（会诊总表id）
     `u_id`：操作人ID，当操作为1.加入会话时，这里的值表示被邀请人ID
     `opra`：操作：1.加入会话 2.离开会话 3.异常中断 4.断线重连
     'inviter_id': 当操作为1.加入会话时，这里的值表示邀请人ID，其他情况为null
     'gmt_create':操作发生时间*/
    /**
     * 1.加入会话
     */
    public static final long OPRA_JOIN_SESSION        = 1L;
    /**
     * 2.离开会话
     */
    public static final long OPRA_LEAVE_SESSION       = 2L;

    /**
     *  3.异常中断
     */
    public static final long OPRA_EXCEPTION_INTERRUPT = 3L;
    /**
     * 4.断线重连
     */
    public static final long OPRA_BREAK_RECONNECTION  = 4L;

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
    @Column(name = "link_id", nullable = false)
    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
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
    @Column(name = "opra", nullable = false)
    public Long getOpra() {
        return opra;
    }

    public void setOpra(Long opra) {
        this.opra = opra;
    }

    @Basic
    @Column(name = "inviter_id", nullable = true)
    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    @Basic
    @Column(name = "gmt_create", nullable = false)
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ConsultDetail that = (ConsultDetail) o;

        if (recId != that.recId)
            return false;
        if (linkId != that.linkId)
            return false;
        if (uId != that.uId)
            return false;
        if (opra != that.opra)
            return false;
        if (inviterId != null ? !inviterId.equals(that.inviterId) : that.inviterId != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (int) (linkId ^ (linkId >>> 32));
        result = 31 * result + (int) (uId ^ (uId >>> 32));
        result = 31 * result + (int) (opra ^ (opra >>> 32));
        result = 31 * result + (inviterId != null ? inviterId.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        return result;
    }
}
