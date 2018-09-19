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
 * 视频图片
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "video_pic", schema = "ambitionj2c", catalog = "")
public class VideoPic {
    private Long   picId;
    private Long   videoId;
    private Long   picType;
    private String picPath;
    private Date   gmtCreate;
    private Date   gmtMod;

    /*`pic_id`：图片id, 
    `video_id`：视频id,
    'pic_type':图片类型:(暂考虑大小图问题)
     `pic_BLOB ` ：图片BLOB ,
     `gmt_create` ：创建时间,
     `gmt_modified` ：修改时间*/
    @Id
    @Column(name = "pic_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    @Basic
    @Column(name = "video_id", nullable = true)
    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Basic
    @Column(name = "pic_type", nullable = true)
    public Long getPicType() {
        return picType;
    }

    public void setPicType(Long picType) {
        this.picType = picType;
    }

    @Basic
    @Column(name = "pic_path", nullable = true, length = 255)
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        VideoPic videoPic = (VideoPic) o;

        if (picId != videoPic.picId)
            return false;
        if (videoId != null ? !videoId.equals(videoPic.videoId) : videoPic.videoId != null)
            return false;
        if (picType != null ? !picType.equals(videoPic.picType) : videoPic.picType != null)
            return false;
        if (picPath != null ? !picPath.equals(videoPic.picPath) : videoPic.picPath != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(videoPic.gmtCreate) : videoPic.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(videoPic.gmtMod) : videoPic.gmtMod != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (picId ^ (picId >>> 32));
        result = 31 * result + (videoId != null ? videoId.hashCode() : 0);
        result = 31 * result + (picType != null ? picType.hashCode() : 0);
        result = 31 * result + (picPath != null ? picPath.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        return result;
    }
}
