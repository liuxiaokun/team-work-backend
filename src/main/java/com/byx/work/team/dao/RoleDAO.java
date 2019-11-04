package com.byx.work.team.dao;

import com.byx.work.team.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@Mapper
public interface RoleDAO extends BaseDAO<Role> {

    /**
     * 查询用户的角色名称列表
     *
     * @param userId 用户id
     * @return 角色名字列表
     */
    List<String> selectRoleNamesByUserId(Long userId);
}
