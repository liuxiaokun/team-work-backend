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
@ApiModel("角色表")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolesDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "角色名字", name = "name")
    private String name;
}
