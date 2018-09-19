package com.nuls.io.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 属性名称表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "property_info", schema = "ambitionj2c", catalog = "")
public class PropertyInfo {
    private Long                 propertyId;
    private String               propertyNameEn;
    private String               propertyNameCn;
    private String               propertyDesc;
    private String               defaultValue;
    private String               tagType;
    private Long                 disableFlag;
    private Long                 globalFlag;
    private Long                 customizeFlag;
    private Long                 displayOrder;
    private Long                 htmlType;
    private Date                 gmtCreate;
    private Date                 gmtMod;
    /*property_id：属性id
    property_name_en：属性英文名称（唯一）
    property_name_cn：属性中午名称
    property_desc：属性描述
    default_value：默认值
    tag_type：标签分类
    disable_flag：使用标志0 失效 1生效
    global_flag：是否公共属性配置  0私有配置 1是公共配置 2.都是
    customize_flag：能否用户自定义 0 不能 1能
    display_order：显示优先级
    html_type：radio,select,checkbox,text*/

    private List<PropertyExtend> propertyExtends;
    private List<RegularLinkage> regularLinkage;
    /**
     * 失效
     */
    public static final Long     DISABLE_FALG_DISABLE       = 0L;
    /**
     * 生效
     */
    public static final Long     DISABLE_FALG_ENABLE        = 1L;
    /**
     * 私有配置
     */
    public static final Long     GLOBAL_FLAG_PRIVATE        = 0L;
    /**
     * 公共配置
     */
    public static final Long     GLOBAL_FLAG_PUBLIC         = 1L;
    /**
     * 既是私有配置  又是公共配置
     */
    public static final Long     GLOBAL_FLAG_PRIVATE_PUBLIC = 2L;
    /**
     * 能否用户自定义 0不能
     */
    public static final Long     CUSTOMIZE_FLAG_DISABLE     = 0L;
    /**
     * 能否用户自定义 1能
     */
    public static final Long     CUSTOMIZE_FLAG_ENABLE      = 1L;
    /**
     * radio类型
     */
    public static final Long     HTML_TYPE_RADIO            = 1L;
    /**
     *  select类型
     */
    public static final Long     HTML_TYPE_SELECT           = 2L;
    /**
     * checkbox类型
     */
    public static final Long     HTML_TYPE_CHECKBOX         = 3L;
    /**
     *  text类型
     */
    public static final Long     HTML_TYPE_TEXT             = 4L;

    @Id
    @Column(name = "property_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    @Basic
    @Column(name = "property_name_en", nullable = false, length = 255)
    public String getPropertyNameEn() {
        return propertyNameEn;
    }

    public void setPropertyNameEn(String propertyNameEn) {
        this.propertyNameEn = propertyNameEn;
    }

    @Basic
    @Column(name = "property_name_cn", nullable = false, length = 255)
    public String getPropertyNameCn() {
        return propertyNameCn;
    }

    public void setPropertyNameCn(String propertyNameCn) {
        this.propertyNameCn = propertyNameCn;
    }

    @Basic
    @Column(name = "property_desc", nullable = true, length = 255)
    public String getPropertyDesc() {
        return propertyDesc;
    }

    public void setPropertyDesc(String propertyDesc) {
        this.propertyDesc = propertyDesc;
    }

    @Basic
    @Column(name = "default_value", nullable = true, length = 255)
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Basic
    @Column(name = "tag_type", nullable = true, length = 255)
    public String getTagType() {
        return tagType;
    }

    /**
     * Setter method for property <tt>tagType</tt>.
     * 
     * @param tagType value to be assigned to property tagType
     */
    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    @Basic
    @Column(name = "disable_flag", nullable = false)
    public Long getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Long disableFlag) {
        this.disableFlag = disableFlag;
    }

    @Basic
    @Column(name = "global_flag", nullable = false)
    public Long getGlobalFlag() {
        return globalFlag;
    }

    public void setGlobalFlag(Long globalFlag) {
        this.globalFlag = globalFlag;
    }

    @Basic
    @Column(name = "customize_flag", nullable = false)
    public Long getCustomizeFlag() {
        return customizeFlag;
    }

    public void setCustomizeFlag(Long customizeFlag) {
        this.customizeFlag = customizeFlag;
    }

    @Basic
    @Column(name = "display_order", nullable = false)
    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Basic
    @Column(name = "html_type", nullable = false)
    public Long getHtmlType() {
        return htmlType;
    }

    public void setHtmlType(Long htmlType) {
        this.htmlType = htmlType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PropertyInfo that = (PropertyInfo) o;

        if (propertyId != that.propertyId)
            return false;
        if (disableFlag != that.disableFlag)
            return false;
        if (globalFlag != that.globalFlag)
            return false;
        if (customizeFlag != that.customizeFlag)
            return false;
        if (displayOrder != that.displayOrder)
            return false;
        if (htmlType != that.htmlType)
            return false;
        if (propertyNameEn != null ? !propertyNameEn.equals(that.propertyNameEn)
            : that.propertyNameEn != null)
            return false;
        if (propertyNameCn != null ? !propertyNameCn.equals(that.propertyNameCn)
            : that.propertyNameCn != null)
            return false;
        if (propertyDesc != null ? !propertyDesc.equals(that.propertyDesc)
            : that.propertyDesc != null)
            return false;
        if (defaultValue != null ? !defaultValue.equals(that.defaultValue)
            : that.defaultValue != null)
            return false;

        return true;
    }

    @Transient
    @ApiModelProperty(hidden = false)
    public List<PropertyExtend> getPropertyExtends() {
        return propertyExtends;
    }

    /**
     * Setter method for property <tt>propertyExtends</tt>.
     * 
     * @param propertyExtends value to be assigned to property propertyExtends
     */
    public void setPropertyExtends(List<PropertyExtend> propertyExtends) {
        this.propertyExtends = propertyExtends;
    }

    @Transient
    @ApiModelProperty(hidden = false)
    public List<RegularLinkage> getRegularLinkage() {
        return regularLinkage;
    }

    /**
     * Setter method for property <tt>regularLinkage</tt>.
     * 
     * @param regularLinkage value to be assigned to property regularLinkage
     */
    public void setRegularLinkage(List<RegularLinkage> regularLinkage) {
        this.regularLinkage = regularLinkage;
    }

    @Override
    public int hashCode() {
        int result = (int) (propertyId ^ (propertyId >>> 32));
        result = 31 * result + (propertyNameEn != null ? propertyNameEn.hashCode() : 0);
        result = 31 * result + (propertyNameCn != null ? propertyNameCn.hashCode() : 0);
        result = 31 * result + (propertyDesc != null ? propertyDesc.hashCode() : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (int) (disableFlag ^ (disableFlag >>> 32));
        result = 31 * result + (int) (globalFlag ^ (globalFlag >>> 32));
        result = 31 * result + (int) (customizeFlag ^ (customizeFlag >>> 32));
        result = 31 * result + (int) (displayOrder ^ (displayOrder >>> 32));
        result = 31 * result + (int) (htmlType ^ (htmlType >>> 32));
        return result;
    }
}
