package com.byx.work.team.service.impl;

import com.byx.work.team.dao.BugStateDAO;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.BugStateDTO;
import com.byx.work.team.model.entity.BugState;
import com.byx.work.team.service.BugStateService;
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
public class BugStateServiceImpl implements BugStateService {

    private final BugStateDAO bugStateDAO;

    @Autowired
    public BugStateServiceImpl(BugStateDAO bugStateDAO) {
        this.bugStateDAO = bugStateDAO;
    }

    @Override
    public void saveBugState(@NonNull BugState bugState) throws BizException {
        log.info("save BugState:{}", bugState);
        if (bugStateDAO.insert(bugState) != 1) {
            log.error("insert error, data:{}", bugState);
            throw new BizException("Insert BugState Error!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBugStateList(@NonNull List<BugState> bugStateList) throws BizException {

        if (bugStateList.size() == 0) {
            throw new BizException("参数长度不能为0");
        }
        int rows = bugStateDAO.insertList(bugStateList);

        if (rows != bugStateList.size()) {
            log.error("数据库实际插入成功数({})与给定的({})不一致", rows, bugStateList.size());
            throw new BizException("批量保存异常");
        }
    }

    @Override
    public void updateBugState(@NonNull BugState bugState) {
        log.info("full update BugState:{}", bugState);
        bugStateDAO.update(bugState);
    }

    @Override
    public void updateBugStateSelective(@NonNull Map<String, Object> dataMap, @NonNull Map<String, Object> conditionMap) {
        log.info("part update dataMap:{}, conditionMap:{}", dataMap, conditionMap);
        Map<String, Object> params = new HashMap<>(2);
        params.put("datas", dataMap);
        params.put("conditions", conditionMap);
        bugStateDAO.updatex(params);
    }

    @Override
    public void logicDeleteBugState(@NonNull Long id, @NonNull Long userId) throws BizException {
        log.info("逻辑删除，数据id:{}, 用户id:{}", id, userId);
        Map<String, Object> params = new HashMap<>(3);
        params.put("id", id);
        params.put("modifiedBy", userId);
        params.put("modifiedDate", System.currentTimeMillis());
        int rows = bugStateDAO.delete(params);

        if (rows != 1) {
            log.error("逻辑删除异常, rows:{}", rows);
            throw new BizException("删除异常.");
        }
    }

    @Override
    public void deleteBugState(@NonNull Long id) throws BizException {
        log.info("物理删除, id:{}", id);
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        int rows = bugStateDAO.pdelete(params);

        if (rows != 1) {
            log.error("删除异常, 实际删除了{}条数据", rows);
            throw new BizException("删除失败");
        }
    }

    @Override
    public BugStateDTO findBugStateById(@NonNull Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        BugState bugState = bugStateDAO.selectOne(params);
        BugStateDTO bugStateDTO = new BugStateDTO();

        if (null != bugState) {
            BeanUtils.copyProperties(bugState, bugStateDTO);
        }
        return bugStateDTO;
    }

    @Override
    public BugStateDTO findOneBugState(Map<String, Object> params) {
        log.info("find one params:{}", params);
        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry -> (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        BugState bugState = bugStateDAO.selectOne(params);
        BugStateDTO bugStateDTO = new BugStateDTO();
        if (null != bugState) {
            BeanUtils.copyProperties(bugState, bugStateDTO);
        }
        return bugStateDTO;
    }

    @Override
    public List<BugStateDTO> find(Map<String, Object> params,
        Vector<SortingContext> scs, PagingContext pc) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        params.put("pc", pc);
        params.put("scs", scs);
        List<BugState> bugStateList = bugStateDAO.select(params);
        List<BugStateDTO> resultList = new ArrayList<>();
        bugStateList.forEach(tem -> {
            BugStateDTO bugStateDTO = new BugStateDTO();
            BeanUtils.copyProperties(tem, bugStateDTO);
            resultList.add(bugStateDTO);
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
        return bugStateDAO.selectMap(params);
    }

    @Override
    public int count(Map<String, Object> params) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return bugStateDAO.count(params);
    }

    @Override
    public Map<String, Integer> groupCount(String group, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(1);
        }
        conditions.put("group", group);
        List<Map<String, Object>> maps = bugStateDAO.groupCount(conditions);
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
        return bugStateDAO.sum(conditions);
    }

    @Override
    public Map<String, Double> groupSum(String group, String sumField, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(2);
        }
        conditions.put("group", group);
        conditions.put("sumfield", sumField);
        List<Map<String, Object>> maps = bugStateDAO.groupSum(conditions);
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
