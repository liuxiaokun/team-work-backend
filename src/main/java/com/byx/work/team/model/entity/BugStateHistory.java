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
public class BugStateHistory extends BaseEntity implements Serializable {

    /**
     * 
     */
    private Long bugId;

    /**
     * 
     */
    private Long bugStateId;

    /**
     * 当前状态的处理人
     */
    private Long assigner;
}
