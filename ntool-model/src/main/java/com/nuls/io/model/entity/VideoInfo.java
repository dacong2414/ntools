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
 * 视频信息
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "video_info", schema = "ambitionj2c", catalog = "")
public class VideoInfo {
    private Long   videoId;
    private String fileStoragePath;
    private String reportId;
    private Long   userId;
    private Long   deviceId;
    private Long   categoryId;
    private Long   videoTime;
    private String videoTitle;
    private Date   gmtCreate;
    private Date   gmtMod;
    private Long   disableFlag;

    /*`video_id`：视频id,
    `file_storage_path` ：视频存储位置,
    `report_id` ：报告id,
    `video_time`：视频时长,
    ‘user_id’:视频上传人ID，
    ‘device_id’:设备ID，
    'category_id':视频类别,
    video_title:视频标题，
    `gmt_create` ：创建时间,
    `gmt_modified` ：修改时间
    'disable_flag':有效标志*/
    @Id
    @Column(name = "video_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Basic
    @Column(name = "file_storage_path", nullable = true, length = 255)
    public String getFileStoragePath() {
        return fileStoragePath;
    }

    public void setFileStoragePath(String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }

    @Basic
    @Column(name = "report_id", nullable = true, length = 255)
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "device_id", nullable = true)
    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "category_id", nullable = true)
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "video_time", nullable = true)
    public Long getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(Long videoTime) {
        this.videoTime = videoTime;
    }

    @Basic
    @Column(name = "video_title", nullable = true, length = 255)
    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
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
    public Date getgmtMod() {
        return gmtMod;
    }

    public void setgmtMod(Date gmtMod) {
        this.gmtMod = gmtMod;
    }

    @Basic
    @Column(name = "disable_flag", nullable = true)
    public Long getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Long disableFlag) {
        this.disableFlag = disableFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        VideoInfo videoInfo = (VideoInfo) o;

        if (videoId != videoInfo.videoId)
            return false;
        if (fileStoragePath != null ? !fileStoragePath.equals(videoInfo.fileStoragePath)
            : videoInfo.fileStoragePath != null)
            return false;
        if (reportId != null ? !reportId.equals(videoInfo.reportId) : videoInfo.reportId != null)
            return false;
        if (userId != null ? !userId.equals(videoInfo.userId) : videoInfo.userId != null)
            return false;
        if (deviceId != null ? !deviceId.equals(videoInfo.deviceId) : videoInfo.deviceId != null)
            return false;
        if (categoryId != null ? !categoryId.equals(videoInfo.categoryId)
            : videoInfo.categoryId != null)
            return false;
        if (videoTime != null ? !videoTime.equals(videoInfo.videoTime)
            : videoInfo.videoTime != null)
            return false;
        if (videoTitle != null ? !videoTitle.equals(videoInfo.videoTitle)
            : videoInfo.videoTitle != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(videoInfo.gmtCreate)
            : videoInfo.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(videoInfo.gmtMod) : videoInfo.gmtMod != null)
            return false;
        if (disableFlag != null ? !disableFlag.equals(videoInfo.disableFlag)
            : videoInfo.disableFlag != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (videoId ^ (videoId >>> 32));
        result = 31 * result + (fileStoragePath != null ? fileStoragePath.hashCode() : 0);
        result = 31 * result + (reportId != null ? reportId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (videoTime != null ? videoTime.hashCode() : 0);
        result = 31 * result + (videoTitle != null ? videoTitle.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        result = 31 * result + (disableFlag != null ? disableFlag.hashCode() : 0);
        return result;
    }
}
