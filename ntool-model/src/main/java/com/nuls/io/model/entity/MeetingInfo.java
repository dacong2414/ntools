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
import com.google.common.collect.Range;
import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.exception.ProcessException;

/**
 * 会议信息表  角色为meetingClient的用户才可以为会议主席
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "meeting_info", schema = "ambitionj2c", catalog = "")
public class MeetingInfo {
    private Long   recId;
    private Long   createUserId;
    private Date   gmtCreate;
    private Date   gmtMod;
    private String meetingName;
    private String meetingPassWord;

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
    @Column(name = "create_user_id", nullable = true)
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
    @Column(name = "meeting_name", nullable = true, length = 255)
    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    @Basic
    @Column(name = "meeting_pass_word", nullable = true, length = 255)
    public String getMeetingPassWord() {
        return meetingPassWord;
    }

    public void setMeetingPassWord(String meetingPassWord) {
        this.meetingPassWord = meetingPassWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MeetingInfo that = (MeetingInfo) o;

        if (recId != that.recId)
            return false;
        if (createUserId != null ? !createUserId.equals(that.createUserId)
            : that.createUserId != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(that.gmtMod) : that.gmtMod != null)
            return false;
        if (meetingName != null ? !meetingName.equals(that.meetingName) : that.meetingName != null)
            return false;
        if (meetingPassWord != null ? !meetingPassWord.equals(that.meetingPassWord)
            : that.meetingPassWord != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (createUserId != null ? createUserId.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        result = 31 * result + (meetingName != null ? meetingName.hashCode() : 0);
        result = 31 * result + (meetingPassWord != null ? meetingPassWord.hashCode() : 0);
        return result;
    }

    /**
     *
     */
    public void check() {
        if (meetingName == null) {
            throw new ProcessException(CodeEnum.ERROR_5031);
        }
        if (!Range.openClosed(0, 50).contains(meetingName.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5022);
        }
        if (meetingPassWord == null) {
            throw new ProcessException(CodeEnum.ERROR_5032);
        }
        if (!Range.openClosed(5, 20).contains(meetingPassWord.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5033);
        }
        if (createUserId == null) {
            throw new ProcessException(CodeEnum.ERROR_5034);
        }

    }
}
