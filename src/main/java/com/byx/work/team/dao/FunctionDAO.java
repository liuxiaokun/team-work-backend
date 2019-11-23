package com.byx.work.team.dao;

import com.byx.work.team.model.dto.CommDTO;
import com.byx.work.team.model.dto.FunctionDTO;
import com.byx.work.team.model.entity.Function;
import com.byx.work.team.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@Mapper
public interface FunctionDAO extends BaseDAO<Function> {

    List<CommDTO> statStateCount(Map<String, Object> params);

    List<FunctionDTO> selectAll(Map<String, Object> params);

    int countAll(Map<String, Object> params);

}
