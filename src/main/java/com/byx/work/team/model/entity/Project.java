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
public class Project extends BaseEntity implements Serializable {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 启动时间
     */
    private Long startTime;

    /**
     * 完成日期
     */
    private Long deadline;

    /**
     * 代码地址
     */
    private String codeGitUrl;

    /**
     * 项目介绍详细信息
     */
    private String desc;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     */
    private String state;
}
