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
 * 视频类别
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "video_category", schema = "ambitionj2c", catalog = "")
public class VideoCategory {
    private Long   categoryId;
    private String categoryName;
    private Date   gmtCreate;
    private Date   gmtMod;
    private Long   disableFlag;

    /* category_id:主键ID  
     `category_name`：类别名称（唯一）,
      `gmt_create` ：创建时间,
      `gmt_modified` ：修改时间
      disableFlag：0无效 1有效*/
    @Id
    @Column(name = "category_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_name", nullable = true, length = 255)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

        VideoCategory that = (VideoCategory) o;

        if (categoryId != that.categoryId)
            return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName)
            : that.categoryName != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(that.gmtMod) : that.gmtMod != null)
            return false;
        if (disableFlag != null ? !disableFlag.equals(that.disableFlag) : that.disableFlag != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (categoryId ^ (categoryId >>> 32));
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        result = 31 * result + (disableFlag != null ? disableFlag.hashCode() : 0);
        return result;
    }
}
