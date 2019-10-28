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
 * @since 2019/10/28
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bug extends BaseEntity implements Serializable {

    /**
     * 
     */
    private Long functionId;

    /**
     * 归属人
     */
    private Long owner;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String desc;

    /**
     * 开始修复时间
     */
    private Long fixStartTime;

    /**
     * 修复结束时间
     */
    private Long fixDeadline;

    /**
     * 
     */
    private Long testDeadline;

    /**
     * 修复上线日期
     */
    private Long deadline;
}
