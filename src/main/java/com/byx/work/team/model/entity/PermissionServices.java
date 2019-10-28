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
public class PermissionServices extends BaseEntity implements Serializable {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 服务url
     */
    private String url;

    /**
     * HTTP方法
     */
    private String method;

    /**
     * 层级关系
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer priority;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;
}
