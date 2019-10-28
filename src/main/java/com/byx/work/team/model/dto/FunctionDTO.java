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
@ApiModel("Function")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "归属项目", name = "projectId")
    private Long projectId;

    @ApiModelProperty(value = "需求名称", name = "name")
    private String name;

    @ApiModelProperty(value = "详细描述", name = "desc")
    private String desc;

    @ApiModelProperty(value = "当前任务状态", name = "currentStateId")
    private Long currentStateId;

    @ApiModelProperty(value = "", name = "currentStateName")
    private String currentStateName;

    @ApiModelProperty(value = "开发开始时间", name = "devStartTime")
    private Long devStartTime;

    @ApiModelProperty(value = "开发完成时间", name = "devDeadline")
    private Long devDeadline;

    @ApiModelProperty(value = "测试完成时间", name = "testDeadline")
    private Long testDeadline;

    @ApiModelProperty(value = "功能完成时间", name = "deadline")
    private Long deadline;
}
