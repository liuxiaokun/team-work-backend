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
@ApiModel("ProjectUser")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectUserDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "", name = "projectId")
    private Long projectId;

    @ApiModelProperty(value = "", name = "userId")
    private Long userId;
}
