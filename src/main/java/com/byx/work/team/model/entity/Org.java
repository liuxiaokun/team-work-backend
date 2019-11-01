package com.byx.work.team.model.entity;

import com.byx.work.team.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.math.BigDecimal;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/11/01
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Org extends BaseEntity implements Serializable {

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String code;

    /**
     * 
     */
    private Integer priority;

    /**
     * 层级关系
     */
    private Long parentId;
}
