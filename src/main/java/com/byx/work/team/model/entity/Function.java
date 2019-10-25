package com.byx.work.team.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/25
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Function extends BaseEntity implements Serializable {

    /**
     * 
     */
    private Long projectId;

    /**
     * 
     */
    private String name;

    /**
     * 详细描述
     */
    private String desc;

    /**
     * 开发完成时间
     */
    private Long devDeadline;

    /**
     * 测试完成时间
     */
    private Long testDeadline;

    /**
     * 功能完成时间
     */
    private Long deadline;
}
