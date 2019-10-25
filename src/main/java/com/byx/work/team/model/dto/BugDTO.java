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

    @ApiModelProperty(value = "", name = "fixDeadline")
    private Long fixDeadline;

    @ApiModelProperty(value = "", name = "testDeadline")
    private Long testDeadline;

    @ApiModelProperty(value = "截止日期", name = "deadline")
    private Long deadline;
}
