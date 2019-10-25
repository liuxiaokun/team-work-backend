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
@ApiModel("Function")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "", name = "projectId")
    private Long projectId;

    @ApiModelProperty(value = "", name = "name")
    private String name;

    @ApiModelProperty(value = "详细描述", name = "desc")
    private String desc;

    @ApiModelProperty(value = "开发完成时间", name = "devDeadline")
    private Long devDeadline;

    @ApiModelProperty(value = "测试完成时间", name = "testDeadline")
    private Long testDeadline;

    @ApiModelProperty(value = "功能完成时间", name = "deadline")
    private Long deadline;
}
