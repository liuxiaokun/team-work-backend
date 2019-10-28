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
public class FunctionState extends BaseEntity implements Serializable {

    /**
     * 状态名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否可用
     */
    private Integer enabled;

    /**
     * 状态优先顺序
     */
    private Integer priority;
}
