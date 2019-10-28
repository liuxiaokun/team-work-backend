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
     * 
     */
    private Long devStartTime;

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
