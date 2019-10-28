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
