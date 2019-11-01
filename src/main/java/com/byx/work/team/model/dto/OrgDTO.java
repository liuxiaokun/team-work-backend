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
@ApiModel("组织机构表")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "", name = "name")
    private String name;

    @ApiModelProperty(value = "", name = "code")
    private String code;

    @ApiModelProperty(value = "", name = "priority")
    private Integer priority;

    @ApiModelProperty(value = "层级关系", name = "parentId")
    private Long parentId;
}
