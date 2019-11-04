package com.byx.work.team.dao;

import com.byx.work.team.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@Mapper
public interface UserDAO extends BaseDAO<User> {

    List<User> selectByOrgIds(Map<String, Object> params);

    int countByOrgIds(Map<String, Object> params);
}
