package com.byx.work.team.dao;

import com.byx.work.team.model.entity.Bug;
import com.byx.work.team.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/11/18
 */
@Mapper
public interface BugDAO extends BaseDAO<Bug> {
}
