package com.byx.work.team.service.impl;

import com.byx.work.team.dao.FunctionStateDAO;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.FunctionStateDTO;
import com.byx.work.team.model.entity.FunctionState;
import com.byx.work.team.service.FunctionStateService;
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
public class FunctionStateServiceImpl implements FunctionStateService {

    private final FunctionStateDAO functionStateDAO;

    @Autowired
    public FunctionStateServiceImpl(FunctionStateDAO functionStateDAO) {
        this.functionStateDAO = functionStateDAO;
    }

    @Override
    public void saveFunctionState(@NonNull FunctionState functionState) throws BizException {
        log.info("save FunctionState:{}", functionState);
        if (functionStateDAO.insert(functionState) != 1) {
            log.error("insert error, data:{}", functionState);
            throw new BizException("Insert FunctionState Error!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFunctionStateList(@NonNull List<FunctionState> functionStateList) throws BizException {

        if (functionStateList.size() == 0) {
            throw new BizException("参数长度不能为0");
        }
        int rows = functionStateDAO.insertList(functionStateList);

        if (rows != functionStateList.size()) {
            log.error("数据库实际插入成功数({})与给定的({})不一致", rows, functionStateList.size());
            throw new BizException("批量保存异常");
        }
    }

    @Override
    public void updateFunctionState(@NonNull FunctionState functionState) {
        log.info("full update FunctionState:{}", functionState);
        functionStateDAO.update(functionState);
    }

    @Override
    public void updateFunctionStateSelective(@NonNull Map<String, Object> dataMap, @NonNull Map<String, Object> conditionMap) {
        log.info("part update dataMap:{}, conditionMap:{}", dataMap, conditionMap);
        Map<String, Object> params = new HashMap<>(2);
        params.put("datas", dataMap);
        params.put("conditions", conditionMap);
        functionStateDAO.updatex(params);
    }

    @Override
    public void logicDeleteFunctionState(@NonNull Long id, @NonNull Long userId) throws BizException {
        log.info("逻辑删除，数据id:{}, 用户id:{}", id, userId);
        Map<String, Object> params = new HashMap<>(3);
        params.put("id", id);
        params.put("modifiedBy", userId);
        params.put("modifiedDate", System.currentTimeMillis());
        int rows = functionStateDAO.delete(params);

        if (rows != 1) {
            log.error("逻辑删除异常, rows:{}", rows);
            throw new BizException("删除异常.");
        }
    }

    @Override
    public void deleteFunctionState(@NonNull Long id) throws BizException {
        log.info("物理删除, id:{}", id);
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        int rows = functionStateDAO.pdelete(params);

        if (rows != 1) {
            log.error("删除异常, 实际删除了{}条数据", rows);
            throw new BizException("删除失败");
        }
    }

    @Override
    public FunctionStateDTO findFunctionStateById(@NonNull Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        FunctionState functionState = functionStateDAO.selectOne(params);
        FunctionStateDTO functionStateDTO = new FunctionStateDTO();

        if (null != functionState) {
            BeanUtils.copyProperties(functionState, functionStateDTO);
        }
        return functionStateDTO;
    }

    @Override
    public FunctionStateDTO findOneFunctionState(Map<String, Object> params) {
        log.info("find one params:{}", params);
        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry -> (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        FunctionState functionState = functionStateDAO.selectOne(params);
        FunctionStateDTO functionStateDTO = new FunctionStateDTO();
        if (null != functionState) {
            BeanUtils.copyProperties(functionState, functionStateDTO);
        }
        return functionStateDTO;
    }

    @Override
    public List<FunctionStateDTO> find(Map<String, Object> params,
        Vector<SortingContext> scs, PagingContext pc) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        params.put("pc", pc);
        params.put("scs", scs);
        List<FunctionState> functionStateList = functionStateDAO.select(params);
        List<FunctionStateDTO> resultList = new ArrayList<>();
        functionStateList.forEach(tem -> {
            FunctionStateDTO functionStateDTO = new FunctionStateDTO();
            BeanUtils.copyProperties(tem, functionStateDTO);
            resultList.add(functionStateDTO);
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
        return functionStateDAO.selectMap(params);
    }

    @Override
    public int count(Map<String, Object> params) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return functionStateDAO.count(params);
    }

    @Override
    public Map<String, Integer> groupCount(String group, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(1);
        }
        conditions.put("group", group);
        List<Map<String, Object>> maps = functionStateDAO.groupCount(conditions);
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
        return functionStateDAO.sum(conditions);
    }

    @Override
    public Map<String, Double> groupSum(String group, String sumField, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(2);
        }
        conditions.put("group", group);
        conditions.put("sumfield", sumField);
        List<Map<String, Object>> maps = functionStateDAO.groupSum(conditions);
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
