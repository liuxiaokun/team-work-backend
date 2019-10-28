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
@ApiModel("BugStateHistory")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BugStateHistoryDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "", name = "bugId")
    private Long bugId;

    @ApiModelProperty(value = "", name = "bugStateId")
    private Long bugStateId;

    @ApiModelProperty(value = "当前状态的处理人", name = "assigner")
    private Long assigner;
}
