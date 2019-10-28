package com.byx.work.team.model.entity;

import com.byx.work.team.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.math.BigDecimal;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity implements Serializable {

    /**
     *  登录手机号 
     */
    private String mobile;

    /**
     *  登录密码 
     */
    private String password;

    /**
     *  密码加密用盐 
     */
    private String salt;

    /**
     *  姓名 
     */
    private String name;

    /**
     *  工号 
     */
    private String serialNumber;

    /**
     *  昵称-用于显示 
     */
    private String nickname;

    /**
     *  职位 
     */
    private String position;

    /**
     *  邮箱 
     */
    private String email;

    /**
     *  状态 
     */
    private String state;
}
