package com.byx.work.team.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

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
     * 开发人员ID
     */
    private Long devPerson;

    /**
     * 测试开始时间
     */
    private Long testStartTime;

    /**
     * 测试人员ID
     */
    private Long testPerson;

    /**
     * 发布开发时间
     */
    private Long deployStartTime;

    /**
     * 部署人员ID
     */
    private Long deployPerson;

    /**
     * 功能完成时间
     */
    private Long deadline;
}
