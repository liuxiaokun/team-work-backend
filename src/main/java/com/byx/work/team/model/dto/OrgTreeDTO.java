package com.byx.work.team.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author liuxiaokun
 * @version 1.0-SNAPSHOT
 * @since 2019/7/4
 */
@Data
public class OrgTreeDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    private String code;
    private String title;
    private Integer priority;
    /**
     * 用于前端展开树节点
     */
    private Boolean expand = true;
    private List<OrgTreeDTO> children;
}
