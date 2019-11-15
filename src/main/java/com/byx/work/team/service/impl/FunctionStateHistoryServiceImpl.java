package com.byx.work.team.service.impl;

import com.byx.work.team.dao.FunctionDAO;
import com.byx.work.team.dao.FunctionStateDAO;
import com.byx.work.team.dao.FunctionStateHistoryDAO;
import com.byx.work.team.dao.UserDAO;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.FunctionStateHistoryDTO;
import com.byx.work.team.model.entity.FunctionStateHistory;
import com.byx.work.team.service.FunctionStateHistoryService;
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
public class FunctionStateHistoryServiceImpl implements FunctionStateHistoryService {

    private final FunctionStateHistoryDAO functionStateHistoryDAO;
    private final UserDAO userDAO;
    private final FunctionStateDAO functionStateDAO;
    private final FunctionDAO functionDAO;

    @Autowired
    public FunctionStateHistoryServiceImpl(FunctionStateHistoryDAO functionStateHistoryDAO, UserDAO userDAO
            , FunctionStateDAO functionStateDAO, FunctionDAO functionDAO) {
        this.functionStateHistoryDAO = functionStateHistoryDAO;
        this.userDAO = userDAO;
        this.functionStateDAO = functionStateDAO;
        this.functionDAO = functionDAO;
    }

    @Override
    public void saveFunctionStateHistory(@NonNull FunctionStateHistory functionStateHistory) throws BizException {
        log.info("save FunctionStateHistory:{}", functionStateHistory);
        if (functionStateHistoryDAO.insert(functionStateHistory) != 1) {
            log.error("insert error, data:{}", functionStateHistory);
            throw new BizException("Insert FunctionStateHistory Error!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFunctionStateHistoryList(@NonNull List<FunctionStateHistory> functionStateHistoryList) throws BizException {

        if (functionStateHistoryList.size() == 0) {
            throw new BizException("参数长度不能为0");
        }
        int rows = functionStateHistoryDAO.insertList(functionStateHistoryList);

        if (rows != functionStateHistoryList.size()) {
            log.error("数据库实际插入成功数({})与给定的({})不一致", rows, functionStateHistoryList.size());
            throw new BizException("批量保存异常");
        }
    }

    @Override
    public void updateFunctionStateHistory(@NonNull FunctionStateHistory functionStateHistory) {
        log.info("full update FunctionStateHistory:{}", functionStateHistory);
        functionStateHistoryDAO.update(functionStateHistory);
    }

    @Override
    public void updateFunctionStateHistorySelective(@NonNull Map<String, Object> dataMap, @NonNull Map<String, Object> conditionMap) {
        log.info("part update dataMap:{}, conditionMap:{}", dataMap, conditionMap);
        Map<String, Object> params = new HashMap<>(2);
        params.put("datas", dataMap);
        params.put("conditions", conditionMap);
        functionStateHistoryDAO.updatex(params);
    }

    @Override
    public void logicDeleteFunctionStateHistory(@NonNull Long id, @NonNull Long userId) throws BizException {
        log.info("逻辑删除，数据id:{}, 用户id:{}", id, userId);
        Map<String, Object> params = new HashMap<>(3);
        params.put("id", id);
        params.put("modifiedBy", userId);
        params.put("modifiedDate", System.currentTimeMillis());
        int rows = functionStateHistoryDAO.delete(params);

        if (rows != 1) {
            log.error("逻辑删除异常, rows:{}", rows);
            throw new BizException("删除异常.");
        }
    }

    @Override
    public void deleteFunctionStateHistory(@NonNull Long id) throws BizException {
        log.info("物理删除, id:{}", id);
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        int rows = functionStateHistoryDAO.pdelete(params);

        if (rows != 1) {
            log.error("删除异常, 实际删除了{}条数据", rows);
            throw new BizException("删除失败");
        }
    }

    @Override
    public FunctionStateHistoryDTO findFunctionStateHistoryById(@NonNull Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        FunctionStateHistory functionStateHistory = functionStateHistoryDAO.selectOne(params);
        FunctionStateHistoryDTO functionStateHistoryDTO = new FunctionStateHistoryDTO();

        if (null != functionStateHistory) {
            BeanUtils.copyProperties(functionStateHistory, functionStateHistoryDTO);
        }
        return functionStateHistoryDTO;
    }

    @Override
    public FunctionStateHistoryDTO findOneFunctionStateHistory(Map<String, Object> params) {
        log.info("find one params:{}", params);
        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry -> (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        FunctionStateHistory functionStateHistory = functionStateHistoryDAO.selectOne(params);
        FunctionStateHistoryDTO functionStateHistoryDTO = new FunctionStateHistoryDTO();
        if (null != functionStateHistory) {
            BeanUtils.copyProperties(functionStateHistory, functionStateHistoryDTO);
        }
        return functionStateHistoryDTO;
    }

    @Override
    public List<FunctionStateHistoryDTO> find(Map<String, Object> params,
                                              Vector<SortingContext> scs, PagingContext pc) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        params.put("pc", pc);
        params.put("scs", scs);
        List<FunctionStateHistory> functionStateHistoryList = functionStateHistoryDAO.select(params);
        List<FunctionStateHistoryDTO> resultList = new ArrayList<>();
        functionStateHistoryList.forEach(tem -> {
            FunctionStateHistoryDTO functionStateHistoryDTO = new FunctionStateHistoryDTO();
            BeanUtils.copyProperties(tem, functionStateHistoryDTO);

            Map<String, Object> para = new HashMap<>(1);
            para.put("id", functionStateHistoryDTO.getAssigner());
            functionStateHistoryDTO.setAssignerName(userDAO.selectOne(para).getName());

            para.put("id", functionStateHistoryDTO.getCreatedBy());
            functionStateHistoryDTO.setCreatedByName(userDAO.selectOne(para).getName());

            para.put("id", functionStateHistoryDTO.getFunctionStateId());
            functionStateHistoryDTO.setFunctionStateName(functionStateDAO.selectOne(para).getName());

            para.put("id", functionStateHistoryDTO.getFunctionId());
            functionStateHistoryDTO.setFunctionName(functionDAO.selectOne(para).getName());
            resultList.add(functionStateHistoryDTO);
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
        return functionStateHistoryDAO.selectMap(params);
    }

    @Override
    public int count(Map<String, Object> params) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return functionStateHistoryDAO.count(params);
    }

    @Override
    public Map<String, Integer> groupCount(String group, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(1);
        }
        conditions.put("group", group);
        List<Map<String, Object>> maps = functionStateHistoryDAO.groupCount(conditions);
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
        return functionStateHistoryDAO.sum(conditions);
    }

    @Override
    public Map<String, Double> groupSum(String group, String sumField, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(2);
        }
        conditions.put("group", group);
        conditions.put("sumfield", sumField);
        List<Map<String, Object>> maps = functionStateHistoryDAO.groupSum(conditions);
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
