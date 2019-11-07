package com.byx.work.team.service.impl;

import com.byx.framework.core.context.AppContext;
import com.byx.work.team.dao.*;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.FunctionDTO;
import com.byx.work.team.model.entity.Function;
import com.byx.work.team.model.entity.FunctionStateHistory;
import com.byx.work.team.model.entity.User;
import com.byx.work.team.service.FunctionService;
import com.byx.framework.core.domain.PagingContext;
import com.byx.framework.core.domain.SortingContext;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
@Service
@Slf4j
public class FunctionServiceImpl implements FunctionService {

    private final FunctionDAO functionDAO;
    private final FunctionStateDAO functionStateDAO;
    private final ProjectDAO projectDAO;
    private final FunctionStateHistoryDAO functionStateHistoryDAO;
    private final UserDAO userDAO;

    @Autowired
    public FunctionServiceImpl(FunctionDAO functionDAO, ProjectDAO projectDAO, FunctionStateDAO functionStateDAO,
                               FunctionStateHistoryDAO functionStateHistoryDAO, UserDAO userDAO) {
        this.functionDAO = functionDAO;
        this.functionStateDAO = functionStateDAO;
        this.projectDAO = projectDAO;
        this.functionStateHistoryDAO = functionStateHistoryDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void saveFunction(@NonNull Function function) throws BizException {
        log.info("save Function:{}", function);

        if (function.getDevStartTime() < function.getCreatedDate()) {
            throw new BizException("项目开始时间不能晚于需求创建时间");
        }

        if (function.getTestStartTime() < function.getDevStartTime()) {
            throw new BizException("项目测试时间不能晚于开发时间");
        }

        if (function.getDeployStartTime() < function.getTestStartTime()) {
            throw new BizException("项目部署时间不能晚于测试时间");
        }

        if (function.getDeadline() < function.getDeployStartTime()) {
            throw new BizException("项目结束时间不能晚于部署时间");
        }

        if(null != function.getCurrentStateId()) {
            Map<String, Object> stateParams = new HashMap<>(1);
            stateParams.put("id", function.getCurrentStateId());
            function.setCurrentStateName(functionStateDAO.selectOne(stateParams).getName());

            // 创建相应的状态变化记录
            FunctionStateHistory functionStateHistory = new FunctionStateHistory();
            functionStateHistory.setAssigner(function.getCreatedBy());
            functionStateHistory.setFunctionId(function.getId());
            functionStateHistory.setFunctionStateId(function.getCurrentStateId());
            functionStateHistory.setStatus(1);
            functionStateHistory.setCreatedBy(function.getCreatedBy());
            functionStateHistory.setCreatedDate(System.currentTimeMillis());
            functionStateHistory.setIp(function.getIp());
            functionStateHistory.setId(AppContext.IdGen.nextId());
            functionStateHistoryDAO.insert(functionStateHistory);
        }
        if (functionDAO.insert(function) != 1) {
            log.error("insert error, data:{}", function);
            throw new BizException("Insert Function Error!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFunctionList(@NonNull List<Function> functionList) throws BizException {

        if (functionList.size() == 0) {
            throw new BizException("参数长度不能为0");
        }
        int rows = functionDAO.insertList(functionList);

        if (rows != functionList.size()) {
            log.error("数据库实际插入成功数({})与给定的({})不一致", rows, functionList.size());
            throw new BizException("批量保存异常");
        }
    }

    @Override
    public void updateFunction(@NonNull Function function) {
        log.info("full update Function:{}", function);
        functionDAO.update(function);
    }

    @Override
    public void updateFunctionSelective(@NonNull Map<String, Object> dataMap, @NonNull Map<String, Object> conditionMap) {
        log.info("part update dataMap:{}, conditionMap:{}", dataMap, conditionMap);
        Map<String, Object> params = new HashMap<>(2);
        params.put("datas", dataMap);
        params.put("conditions", conditionMap);
        functionDAO.updatex(params);
    }

    @Override
    public void logicDeleteFunction(@NonNull Long id, @NonNull Long userId) throws BizException {
        log.info("逻辑删除，数据id:{}, 用户id:{}", id, userId);
        Map<String, Object> params = new HashMap<>(3);
        params.put("id", id);
        params.put("modifiedBy", userId);
        params.put("modifiedDate", System.currentTimeMillis());
        int rows = functionDAO.delete(params);

        if (rows != 1) {
            log.error("逻辑删除异常, rows:{}", rows);
            throw new BizException("删除异常.");
        }
    }

    @Override
    public void deleteFunction(@NonNull Long id) throws BizException {
        log.info("物理删除, id:{}", id);
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        int rows = functionDAO.pdelete(params);

        if (rows != 1) {
            log.error("删除异常, 实际删除了{}条数据", rows);
            throw new BizException("删除失败");
        }
    }

    @Override
    public FunctionDTO findFunctionById(@NonNull Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        Function function = functionDAO.selectOne(params);
        FunctionDTO functionDTO = new FunctionDTO();

        if (null != function.getId()) {
            BeanUtils.copyProperties(function, functionDTO);
            Map<String, Object> projectParam = new HashMap<>(1);
            projectParam.put("id", functionDTO.getProjectId());
            functionDTO.setProjectName(projectDAO.selectOne(projectParam).getName());

            if (null != function.getCurrentStateId()) {
                Map<String, Object> historyParams = new HashMap<>(2);
                historyParams.put("functionId", functionDTO.getId());
                historyParams.put("functionStateId", functionDTO.getCurrentStateId());
                FunctionStateHistory functionStateHistory = functionStateHistoryDAO.selectOne(historyParams);

                if(null != functionStateHistory.getId()) {
                    Map<String, Object> userParams = new HashMap<>(1);
                    userParams.put("id", functionStateHistory.getAssigner());
                    User assigner = userDAO.selectOne(userParams);
                    functionDTO.setCurrentHandlePerson(assigner.getName());
                }
            }

            Map<String, Object> createdByParams = new HashMap<>(1);
            createdByParams.put("id", functionDTO.getCreatedBy());
            User createdUser = userDAO.selectOne(createdByParams);
            functionDTO.setCreatedName(createdUser.getName());
        }
        return functionDTO;
    }

    @Override
    public FunctionDTO findOneFunction(Map<String, Object> params) {
        log.info("find one params:{}", params);
        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry -> (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        Function function = functionDAO.selectOne(params);
        FunctionDTO functionDTO = new FunctionDTO();
        if (null != function) {
            BeanUtils.copyProperties(function, functionDTO);
            Map<String, Object> projectParam = new HashMap<>(1);
            projectParam.put("id", functionDTO.getProjectId());
            functionDTO.setProjectName(projectDAO.selectOne(projectParam).getName());
        }
        return functionDTO;
    }

    @Override
    public List<FunctionDTO> find(Map<String, Object> params,
                                  Vector<SortingContext> scs, PagingContext pc) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        params.put("pc", pc);
        params.put("scs", scs);
        List<Function> functionList = functionDAO.select(params);
        List<FunctionDTO> resultList = new ArrayList<>();
        functionList.forEach(tem -> {
            FunctionDTO functionDTO = new FunctionDTO();
            BeanUtils.copyProperties(tem, functionDTO);
            Map<String, Object> projectParam = new HashMap<>(1);
            projectParam.put("id", functionDTO.getProjectId());
            functionDTO.setProjectName(projectDAO.selectOne(projectParam).getName());

            Long deadline = functionDTO.getDeadline();
            Long devStartTime = functionDTO.getDevStartTime();
            long now = System.currentTimeMillis();
            int result = 0;
            if (now > deadline) {
                result = 100;
            } else if (now > devStartTime) {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);

                BigDecimal usedTime = new BigDecimal(now - devStartTime);
                BigDecimal allTime = new BigDecimal(deadline - devStartTime);
                result = usedTime.divide(allTime, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
            }
            functionDTO.setTimeCostPercent(result);
            resultList.add(functionDTO);
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
        return functionDAO.selectMap(params);
    }

    @Override
    public int count(Map<String, Object> params) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return functionDAO.count(params);
    }

    @Override
    public Map<String, Integer> groupCount(String group, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(1);
        }
        conditions.put("group", group);
        List<Map<String, Object>> maps = functionDAO.groupCount(conditions);
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
        return functionDAO.sum(conditions);
    }

    @Override
    public Map<String, Double> groupSum(String group, String sumField, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(2);
        }
        conditions.put("group", group);
        conditions.put("sumfield", sumField);
        List<Map<String, Object>> maps = functionDAO.groupSum(conditions);
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
