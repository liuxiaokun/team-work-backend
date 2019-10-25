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
    private Long fixDeadline;

    /**
     * 
     */
    private Long testDeadline;

    /**
     * 截止日期
     */
    private Long deadline;
}
