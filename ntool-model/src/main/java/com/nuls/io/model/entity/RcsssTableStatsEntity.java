package com.nuls.io.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;


/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.db, v 0.1 2017/11/22 11:22 hhu Exp $$
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "rcsss_table_stats")
public class RcsssTableStatsEntity implements Serializable {

    /** */
    private static final long serialVersionUID = 8404944993763606983L;

    @Id
    @Basic
    @Column(name = "table_name", nullable = false, length = 64)
    private String            tableName;

    @Basic
    @Column(name = "counter")
    private Long              counter;

    @Basic
    @Column(name = "last_cols_update")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp         lastColsUpdate;

    @Basic
    @Column(name = "last_row_update")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp         lastRowUpdate;

    @Basic
    @Column(name = "last_num_rows")
    private Long              lastNumRows;

}