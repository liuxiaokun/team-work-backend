package com.byx.work.team.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/25
 */
@ApiModel("Project")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "项目名称", name = "name")
    private String name;

    @ApiModelProperty(value = "启动时间", name = "startTime")
    private Long startTime;

    @ApiModelProperty(value = "完成日期", name = "deadline")
    private Long deadline;

    @ApiModelProperty(value = "代码地址", name = "codeGitUrl")
    private String codeGitUrl;

    @ApiModelProperty(value = "项目介绍详细信息", name = "desc")
    private String desc;

    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

    @ApiModelProperty(value = "", name = "state")
    private String state;
}
