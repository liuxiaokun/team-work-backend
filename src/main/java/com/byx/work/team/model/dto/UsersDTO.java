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
@ApiModel("用户表")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = " 登录手机号 ", name = "mobile")
    private String mobile;

    @ApiModelProperty(value = " 登录密码 ", name = "password")
    private String password;

    @ApiModelProperty(value = " 密码加密用盐 ", name = "salt")
    private String salt;

    @ApiModelProperty(value = " 姓名 ", name = "name")
    private String name;

    @ApiModelProperty(value = " 工号 ", name = "serialNumber")
    private String serialNumber;

    @ApiModelProperty(value = " 昵称-用于显示 ", name = "nickname")
    private String nickname;

    @ApiModelProperty(value = " 职位 ", name = "position")
    private String position;

    @ApiModelProperty(value = " 邮箱 ", name = "email")
    private String email;

    @ApiModelProperty(value = " 状态 ", name = "state")
    private String state;
}
