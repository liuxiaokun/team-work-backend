package com.byx.work.team.model.dto;

import com.byx.work.team.model.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.math.BigDecimal;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@ApiModel("服务表")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionServiceDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "权限名称", name = "name")
    private String name;

    @ApiModelProperty(value = "服务url", name = "url")
    private String url;

    @ApiModelProperty(value = "HTTP方法", name = "method")
    private String method;

    @ApiModelProperty(value = "层级关系", name = "parentId")
    private Long parentId;

    @ApiModelProperty(value = "排序", name = "priority")
    private Integer priority;

    @ApiModelProperty(value = "图标", name = "icon")
    private String icon;

    @ApiModelProperty(value = "描述", name = "description")
    private String description;
}
