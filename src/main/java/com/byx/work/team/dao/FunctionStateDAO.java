package com.byx.work.team.dao;

import com.byx.work.team.model.entity.FunctionState;
import com.byx.work.team.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@Mapper
public interface FunctionStateDAO extends BaseDAO<FunctionState> {

    FunctionState queryNextState(Map<String, Object> params);
}
