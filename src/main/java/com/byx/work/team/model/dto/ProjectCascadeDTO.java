package com.byx.work.team.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectCascadeDTO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long value;

    private String label;

    private Boolean disabled;

    private List<ProjectCascadeDTO> children;
}
