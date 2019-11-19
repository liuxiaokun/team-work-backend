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
 * @since 2019/11/18
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
     * 
     */
    private Long currentStateId;

    /**
     * 
     */
    private String currentStateName;

    /**
     * 开始修复时间
     */
    private Long fixStartTime;

    /**
     * 测试开始时间
     */
    private Long testStartTime;

    /**
     * 部署开始时间
     */
    private Long deployStartTime;

    /**
     * 修复上线日期
     */
    private Long deadline;
}
