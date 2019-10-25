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
@ApiModel("FunctionStateHistory")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionStateHistoryDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "", name = "functionId")
    private Long functionId;

    @ApiModelProperty(value = "", name = "functionStateId")
    private Long functionStateId;
}
