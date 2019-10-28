package com.byx.work.team.service.impl;

import com.byx.work.team.dao.RolePermissionsDAO;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.RolePermissionsDTO;
import com.byx.work.team.model.entity.RolePermissions;
import com.byx.work.team.service.RolePermissionsService;
import com.byx.framework.core.domain.PagingContext;
import com.byx.framework.core.domain.SortingContext;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@Service
@Slf4j
public class RolePermissionsServiceImpl implements RolePermissionsService {

    private final RolePermissionsDAO rolePermissionsDAO;

    @Autowired
    public RolePermissionsServiceImpl(RolePermissionsDAO rolePermissionsDAO) {
        this.rolePermissionsDAO = rolePermissionsDAO;
    }

    @Override
    public void saveRolePermissions(@NonNull RolePermissions rolePermissions) throws BizException {
        log.info("save RolePermissions:{}", rolePermissions);
        if (rolePermissionsDAO.insert(rolePermissions) != 1) {
            log.error("insert error, data:{}", rolePermissions);
            throw new BizException("Insert RolePermissions Error!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolePermissionsList(@NonNull List<RolePermissions> rolePermissionsList) throws BizException {

        if (rolePermissionsList.size() == 0) {
            throw new BizException("参数长度不能为0");
        }
        int rows = rolePermissionsDAO.insertList(rolePermissionsList);

        if (rows != rolePermissionsList.size()) {
            log.error("数据库实际插入成功数({})与给定的({})不一致", rows, rolePermissionsList.size());
            throw new BizException("批量保存异常");
        }
    }

    @Override
    public void updateRolePermissions(@NonNull RolePermissions rolePermissions) {
        log.info("full update RolePermissions:{}", rolePermissions);
        rolePermissionsDAO.update(rolePermissions);
    }

    @Override
    public void updateRolePermissionsSelective(@NonNull Map<String, Object> dataMap, @NonNull Map<String, Object> conditionMap) {
        log.info("part update dataMap:{}, conditionMap:{}", dataMap, conditionMap);
        Map<String, Object> params = new HashMap<>(2);
        params.put("datas", dataMap);
        params.put("conditions", conditionMap);
        rolePermissionsDAO.updatex(params);
    }

    @Override
    public void logicDeleteRolePermissions(@NonNull Long id, @NonNull Long userId) throws BizException {
        log.info("逻辑删除，数据id:{}, 用户id:{}", id, userId);
        Map<String, Object> params = new HashMap<>(3);
        params.put("id", id);
        params.put("modifiedBy", userId);
        params.put("modifiedDate", System.currentTimeMillis());
        int rows = rolePermissionsDAO.delete(params);

        if (rows != 1) {
            log.error("逻辑删除异常, rows:{}", rows);
            throw new BizException("删除异常.");
        }
    }

    @Override
    public void deleteRolePermissions(@NonNull Long id) throws BizException {
        log.info("物理删除, id:{}", id);
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        int rows = rolePermissionsDAO.pdelete(params);

        if (rows != 1) {
            log.error("删除异常, 实际删除了{}条数据", rows);
            throw new BizException("删除失败");
        }
    }

    @Override
    public RolePermissionsDTO findRolePermissionsById(@NonNull Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        RolePermissions rolePermissions = rolePermissionsDAO.selectOne(params);
        RolePermissionsDTO rolePermissionsDTO = new RolePermissionsDTO();

        if (null != rolePermissions) {
            BeanUtils.copyProperties(rolePermissions, rolePermissionsDTO);
        }
        return rolePermissionsDTO;
    }

    @Override
    public RolePermissionsDTO findOneRolePermissions(Map<String, Object> params) {
        log.info("find one params:{}", params);
        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry -> (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        RolePermissions rolePermissions = rolePermissionsDAO.selectOne(params);
        RolePermissionsDTO rolePermissionsDTO = new RolePermissionsDTO();
        if (null != rolePermissions) {
            BeanUtils.copyProperties(rolePermissions, rolePermissionsDTO);
        }
        return rolePermissionsDTO;
    }

    @Override
    public List<RolePermissionsDTO> find(Map<String, Object> params,
        Vector<SortingContext> scs, PagingContext pc) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        params.put("pc", pc);
        params.put("scs", scs);
        List<RolePermissions> rolePermissionsList = rolePermissionsDAO.select(params);
        List<RolePermissionsDTO> resultList = new ArrayList<>();
        rolePermissionsList.forEach(tem -> {
            RolePermissionsDTO rolePermissionsDTO = new RolePermissionsDTO();
            BeanUtils.copyProperties(tem, rolePermissionsDTO);
            resultList.add(rolePermissionsDTO);
        });

        return resultList;
    }

    @Override
    public List<Map> findMap(Map<String, Object> params, Vector<SortingContext> scs,
                          PagingContext pc, String... columns) throws BizException {
        if (columns.length == 0) {
            throw new BizException("columns长度不能为0");
        }
        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        params.put("pc", pc);
        params.put("scs", scs);
        params.put("columns", columns);
        return rolePermissionsDAO.selectMap(params);
    }

    @Override
    public int count(Map<String, Object> params) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return rolePermissionsDAO.count(params);
    }

    @Override
    public Map<String, Integer> groupCount(String group, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(1);
        }
        conditions.put("group", group);
        List<Map<String, Object>> maps = rolePermissionsDAO.groupCount(conditions);
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Map<String, Object> m : maps) {
            String key = m.get("group") != null ? m.get("group").toString() : "group";
            Object value = m.get("count");
            int count = 0;
            if (StringUtils.hasLength(value.toString())) {
                count = Integer.parseInt(value.toString());
            }
            map.put(key, count);
        }
        return map;
    }

    @Override
    public Double sum(String sumField, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(2);
        }
        conditions.put("sumfield", sumField);
        return rolePermissionsDAO.sum(conditions);
    }

    @Override
    public Map<String, Double> groupSum(String group, String sumField, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(2);
        }
        conditions.put("group", group);
        conditions.put("sumfield", sumField);
        List<Map<String, Object>> maps = rolePermissionsDAO.groupSum(conditions);
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        for (Map<String, Object> m : maps) {
            String key = m.get("group") != null ? m.get("group").toString() : "group";
            Object value = m.get("sum");
            double sum = 0d;
            if (StringUtils.hasLength(value.toString())) {
                sum = Double.parseDouble(value.toString());
            }
            map.put(key, sum);
        }
        return map;
    }
}
