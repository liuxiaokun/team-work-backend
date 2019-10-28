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
@ApiModel("菜单分配表")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionMenusDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "菜单名", name = "name")
    private String name;

    @ApiModelProperty(value = "vue路由的模块", name = "code")
    private String code;

    @ApiModelProperty(value = "", name = "url")
    private String url;

    @ApiModelProperty(value = "层级关系", name = "parentId")
    private Long parentId;

    @ApiModelProperty(value = "优先级排序", name = "priority")
    private Integer priority;
}
