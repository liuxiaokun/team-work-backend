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
 * @since 2019/11/01
 */
@ApiModel("用户组织表")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserOrgDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "", name = "userId")
    private Long userId;

    @ApiModelProperty(value = "", name = "orgId")
    private Long orgId;
}
