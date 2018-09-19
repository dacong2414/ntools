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
 * 视频播报
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "video_play", schema = "ambitionj2c", catalog = "")
public class VideoPlay {
    private Long recId;
    private Long videoId;
    private Long monthePlay;
    private Long totlePlay;
    private Date gmtCreate;

    /*rec_id:主键ID  
    `video_id`：视频ID(唯一),
    monthe_play:月点击数,
    totle_play:总点击数*/
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
    @Column(name = "video_id", nullable = true)
    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Basic
    @Column(name = "monthe_play", nullable = true)
    public Long getMonthePlay() {
        return monthePlay;
    }

    public void setMonthePlay(Long monthePlay) {
        this.monthePlay = monthePlay;
    }

    @Basic
    @Column(name = "totle_play", nullable = true)
    public Long getTotlePlay() {
        return totlePlay;
    }

    public void setTotlePlay(Long totlePlay) {
        this.totlePlay = totlePlay;
    }

    @Basic
    @Column(name = "gmt_create", nullable = true)
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

        VideoPlay videoPlay = (VideoPlay) o;

        if (recId != videoPlay.recId)
            return false;
        if (videoId != null ? !videoId.equals(videoPlay.videoId) : videoPlay.videoId != null)
            return false;
        if (monthePlay != null ? !monthePlay.equals(videoPlay.monthePlay)
            : videoPlay.monthePlay != null)
            return false;
        if (totlePlay != null ? !totlePlay.equals(videoPlay.totlePlay)
            : videoPlay.totlePlay != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(videoPlay.gmtCreate)
            : videoPlay.gmtCreate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (videoId != null ? videoId.hashCode() : 0);
        result = 31 * result + (monthePlay != null ? monthePlay.hashCode() : 0);
        result = 31 * result + (totlePlay != null ? totlePlay.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        return result;
    }
}
