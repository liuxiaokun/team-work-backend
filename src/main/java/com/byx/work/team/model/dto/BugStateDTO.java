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
@ApiModel("BugState")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BugStateDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "名字", name = "name")
    private String name;

    @ApiModelProperty(value = "优先级", name = "priority")
    private Integer priority;

    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

    @ApiModelProperty(value = "是否可用", name = "enabled")
    private Integer enabled;
}
