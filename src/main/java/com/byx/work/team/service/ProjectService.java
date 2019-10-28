package com.byx.work.team.service;

import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.ProjectDTO;
import com.byx.work.team.model.entity.Project;
import com.byx.framework.core.domain.PagingContext;
import com.byx.framework.core.domain.SortingContext;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
public interface ProjectService {

    /**
     * 保存一条 Project 数据。
     *
     * @param project 待保存的数据。
     * @throws BizException 保存失败异常。
     */
    void saveProject(Project project) throws BizException;

    /**
     * 保存多条 Project 数据。
     *
     * @param projectList 待保存的数据列表。
     * @throws BizException 保存失败异常。
     */
    void saveProjectList(List<Project> projectList) throws BizException;

    /**
     * 根据id更新 Project，字段为null的选项会把数据库字段更新为null，即全部更新。
     *
     * @param project 更新的目标数据。
     */
    void updateProject(Project project);

    /**
     * 根据Id部分更新实体 Project。
     *
     * @param dataMap 需要更新的键值对。
     * @param conditionMap where语句后的条件筛选的键值对。
     */
    void updateProjectSelective(Map<String, Object> dataMap, Map<String, Object> conditionMap);

    /**
     * 根据id逻辑删除一条 Project。
     *
     * @param id 数据id。
     * @param userId 删除人的id。
     * @throws BizException 逻辑删除异常。
     */
    void logicDeleteProject(Long id, Long userId) throws BizException;

    /**
     * 根据id物理删除一条 Project。
     *
     * @param id 数据唯一id。
     * @throws BizException 物理删除异常。
     */
    void deleteProject(Long id) throws BizException;

    /**
     * 根据id查询一条 Project。
     *
     * @param id 数据唯一id。
     * @return 查询到的 Project 数据。
     */
    ProjectDTO findProjectById(Long id);

    /**
     * 根据条件查询得到第一条 Project。
     *
     * @param params 查询条件
     * @return 符合条件的一个 Project。
     */
    ProjectDTO findOneProject(Map<String, Object> params);

    /**
     * 根据查询条件得到数据列表，包含分页和排序信息。
     *
     * @param params 查询条件。
     * @param scs 排序信息。
     * @param pc 分页信息。
     * @return 查询结果的数据集合。
     */
    List<ProjectDTO> find(Map<String, Object> params, Vector<SortingContext> scs, PagingContext pc);

    /**
     * 根据查询条件得到指定字段集合的数据列表，包含分页和排序信息。
     *
     * @param params 查询条件。
     * @param columns 需要查询的字段信息。
     * @param scs 排序信息。
     * @param pc 分页信息。
     * @throws BizException 查询异常。
     * @return 查询结果的数据集合。
     */
    List<Map> findMap(Map<String, Object> params, Vector<SortingContext> scs,
                      PagingContext pc, String... columns) throws BizException;

    /**
     * 统计符合条件的数据条数。
     *
     * @param params 统计的过滤条件。
     * @return 统计结果。
     */
    int count(Map<String, Object> params);

    /**
     * 根据给定字段以及查询条件进行分组查询，并统计id的count。
     *
     * @param group      分组的字段。
     * @param conditions 查询的where条件。
     * @return 查询结果 key为查询字段的值，value为查询字段的统计条数。
     */
    Map<String, Integer> groupCount(String group, Map<String, Object> conditions);

    /**
     * 根据给定字段查询统计字段的sum结果。
     *
     * @param sumField   sumField 统计的字段名。
     * @param conditions 查询的where条件。
     * @return 返回sum计算的结果值。
     */
    Double sum(String sumField, Map<String, Object> conditions);

    /**
     * 根据给定字段以及查询条件进行分组查询，并sum统计Field。
     *
     * @param group      分组的字段。
     * @param sumField   sumField 统计的字段名。
     * @param conditions 查询的where条件。
     * @return 查询结果 key为查询字段的值，value为查询字段的求和。
     */
    Map<String, Double> groupSum(String group, String sumField, Map<String, Object> conditions);
}