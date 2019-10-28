package com.byx.work.team.model.dto;

import com.byx.work.team.config.LongDateSerializer;
import com.byx.work.team.model.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@ApiModel("Project")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "项目名称", name = "name")
    private String name;

    @ApiModelProperty(value = "启动时间", name = "startTime")
    @JsonSerialize(using = LongDateSerializer.class)
    private Long startTime;

    @ApiModelProperty(value = "完成日期", name = "deadline")
    @JsonSerialize(using = LongDateSerializer.class)
    private Long deadline;

    @ApiModelProperty(value = "代码地址", name = "codeGitUrl")
    private String codeGitUrl;

    @ApiModelProperty(value = "项目介绍详细信息", name = "desc")
    private String desc;

    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

    @ApiModelProperty(value = "", name = "state")
    private String state;

    private Integer completePercent;
}
