package com.byx.work.team.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuxiaokun
 * @version 1.0-SNAPSHOT
 * @since 2019/11/21
 */
@Data
public class CommDTO implements Serializable {

    private String name;

    private Integer value;
}
