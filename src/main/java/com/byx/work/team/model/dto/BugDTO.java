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
 * @since 2019/11/18
 */
@ApiModel("Bug")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BugDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "", name = "functionId")
    private Long functionId;

    @ApiModelProperty(value = "归属人", name = "owner")
    private Long owner;

    @ApiModelProperty(value = "", name = "title")
    private String title;

    @ApiModelProperty(value = "", name = "desc")
    private String desc;

    @ApiModelProperty(value = "", name = "currentStateId")
    private Long currentStateId;

    @ApiModelProperty(value = "", name = "currentStateName")
    private String currentStateName;

    @ApiModelProperty(value = "开始修复时间", name = "fixStartTime")
    private Long fixStartTime;

    @ApiModelProperty(value = "测试开始时间", name = "testStartTime")
    private Long testStartTime;

    @ApiModelProperty(value = "部署开始时间", name = "deployStartTime")
    private Long deployStartTime;

    @ApiModelProperty(value = "修复上线日期", name = "deadline")
    private Long deadline;
}
