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
public class Function extends BaseEntity implements Serializable {

    /**
     * 归属项目
     */
    private Long projectId;

    /**
     * 需求名称
     */
    private String name;

    /**
     * 详细描述
     */
    private String desc;

    /**
     * 当前任务状态
     */
    private Long currentStateId;

    /**
     * 
     */
    private String currentStateName;

    /**
     * 开发开始时间
     */
    private Long devStartTime;

    /**
     * 测试开始时间
     */
    private Long testStartTime;

    /**
     * 发布开发时间
     */
    private Long deployStartTime;

    /**
     * 功能完成时间
     */
    private Long deadline;
}
