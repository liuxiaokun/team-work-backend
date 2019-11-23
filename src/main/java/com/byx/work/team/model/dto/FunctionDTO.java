package com.byx.work.team.model.dto;

import com.byx.work.team.config.LongDateSerializer;
import com.byx.work.team.model.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@ApiModel("Function")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "归属项目", name = "projectId")
    private Long projectId;

    @ApiModelProperty(value = "归属项目名", name = "projectName")
    private String projectName;

    @ApiModelProperty(value = "需求名称", name = "name")
    private String name;

    @ApiModelProperty(value = "详细描述", name = "desc")
    private String desc;

    @ApiModelProperty(value = "当前任务状态ID", name = "currentStateId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long currentStateId;

    @ApiModelProperty(value = "当前任务状态", name = "currentStateName")
    private String currentStateName;

    @ApiModelProperty(value = "当前处理人姓名", name = "currentAssignerName")
    private String currentHandlePersonName;

    private String currentHandlePerson;

    private Long assigner;

    @ApiModelProperty(value = "开发开始时间", name = "devStartTime")
    private Long devStartTime;

    @ApiModelProperty(value = "测试开始时间", name = "testStartTime")
    private Long testStartTime;

    @ApiModelProperty(value = "发布开始时间", name = "deployStartTime")
    private Long deployStartTime;

    @ApiModelProperty(value = "功能完成时间", name = "deadline")
    private Long deadline;

    private String createdName;
    private Integer completePercent;
    private Integer timeCostPercent;
}
