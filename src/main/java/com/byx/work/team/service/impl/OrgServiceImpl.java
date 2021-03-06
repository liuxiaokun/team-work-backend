package com.byx.work.team.service.impl;

import com.byx.work.team.dao.OrgDAO;
import com.byx.work.team.dao.RoleDAO;
import com.byx.work.team.dao.UserDAO;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.OrgDTO;
import com.byx.work.team.model.dto.OrgTreeDTO;
import com.byx.work.team.model.dto.UserDTO;
import com.byx.work.team.model.entity.Org;
import com.byx.work.team.model.entity.User;
import com.byx.work.team.service.OrgService;
import com.byx.framework.core.domain.PagingContext;
import com.byx.framework.core.domain.SortingContext;
import com.byx.work.team.utils.BeanUtil;
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
 * @since 2019/11/01
 */
@Service
@Slf4j
public class OrgServiceImpl implements OrgService {

    private final OrgDAO orgDAO;
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    @Autowired
    public OrgServiceImpl(OrgDAO orgDAO, UserDAO userDAO, RoleDAO roleDAO) {
        this.orgDAO = orgDAO;
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public void saveOrg(@NonNull Org org) throws BizException {
        log.info("save Org:{}", org);
        if (orgDAO.insert(org) != 1) {
            log.error("insert error, data:{}", org);
            throw new BizException("Insert Org Error!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrgList(@NonNull List<Org> orgList) throws BizException {

        if (orgList.size() == 0) {
            throw new BizException("参数长度不能为0");
        }
        int rows = orgDAO.insertList(orgList);

        if (rows != orgList.size()) {
            log.error("数据库实际插入成功数({})与给定的({})不一致", rows, orgList.size());
            throw new BizException("批量保存异常");
        }
    }

    @Override
    public void updateOrg(@NonNull Org org) {
        log.info("full update Org:{}", org);
        orgDAO.update(org);
    }

    @Override
    public void updateOrgSelective(@NonNull Map<String, Object> dataMap, @NonNull Map<String, Object> conditionMap) {
        log.info("part update dataMap:{}, conditionMap:{}", dataMap, conditionMap);
        Map<String, Object> params = new HashMap<>(2);
        params.put("datas", dataMap);
        params.put("conditions", conditionMap);
        orgDAO.updatex(params);
    }

    @Override
    public void logicDeleteOrg(@NonNull Long id, @NonNull Long userId) throws BizException {
        log.info("逻辑删除，数据id:{}, 用户id:{}", id, userId);
        Map<String, Object> params = new HashMap<>(3);
        params.put("id", id);
        params.put("modifiedBy", userId);
        params.put("modifiedDate", System.currentTimeMillis());
        int rows = orgDAO.delete(params);

        if (rows != 1) {
            log.error("逻辑删除异常, rows:{}", rows);
            throw new BizException("删除异常.");
        }
    }

    @Override
    public void deleteOrg(@NonNull Long id) throws BizException {
        log.info("物理删除, id:{}", id);
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        int rows = orgDAO.pdelete(params);

        if (rows != 1) {
            log.error("删除异常, 实际删除了{}条数据", rows);
            throw new BizException("删除失败");
        }
    }

    @Override
    public OrgDTO findOrgById(@NonNull Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        Org org = orgDAO.selectOne(params);
        OrgDTO orgDTO = new OrgDTO();

        if (null != org) {
            BeanUtils.copyProperties(org, orgDTO);
        }
        return orgDTO;
    }

    @Override
    public OrgDTO findOneOrg(Map<String, Object> params) {
        log.info("find one params:{}", params);
        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry -> (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        Org org = orgDAO.selectOne(params);
        OrgDTO orgDTO = new OrgDTO();
        if (null != org) {
            BeanUtils.copyProperties(org, orgDTO);
        }
        return orgDTO;
    }

    @Override
    public List<OrgDTO> find(Map<String, Object> params,
                             Vector<SortingContext> scs, PagingContext pc) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        params.put("pc", pc);
        params.put("scs", scs);
        List<Org> orgList = orgDAO.select(params);
        List<OrgDTO> resultList = new ArrayList<>();
        orgList.forEach(tem -> {
            OrgDTO orgDTO = new OrgDTO();
            BeanUtils.copyProperties(tem, orgDTO);
            resultList.add(orgDTO);
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
        return orgDAO.selectMap(params);
    }

    @Override
    public int count(Map<String, Object> params) {

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return orgDAO.count(params);
    }

    @Override
    public Map<String, Integer> groupCount(String group, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(1);
        }
        conditions.put("group", group);
        List<Map<String, Object>> maps = orgDAO.groupCount(conditions);
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
        return orgDAO.sum(conditions);
    }

    @Override
    public Map<String, Double> groupSum(String group, String sumField, Map<String, Object> conditions) {
        if (conditions == null) {
            conditions = new HashMap<>(2);
        }
        conditions.put("group", group);
        conditions.put("sumfield", sumField);
        List<Map<String, Object>> maps = orgDAO.groupSum(conditions);
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

    @Override
    public List<OrgTreeDTO> tree(Long parentId, Map<String, Object> param) {
        Map<String, Object> params = new HashMap<>(1);
        Vector<SortingContext> scs = new Vector<>();
        SortingContext sortingContext = new SortingContext();
        sortingContext.setField("priority");
        sortingContext.setOrder("desc");
        scs.add(sortingContext);
        params.put("scs", scs);
        List<Org> originData = orgDAO.select(params);
        return getChildren(parentId, originData);
    }

    private List<OrgTreeDTO> getChildren(Long parentId, List<Org> originData) {
        List<OrgTreeDTO> childList = new ArrayList<>();
        for (Org org : originData) {
            if (org.getParentId().equals(parentId)) {
                OrgTreeDTO dto = new OrgTreeDTO();
                dto.setId(org.getId());
                dto.setParentId(parentId);
                dto.setCode(org.getCode());
                dto.setTitle(org.getName());
                dto.setPriority(org.getPriority());
                childList.add(dto);
            }
        }
        for (OrgTreeDTO orgTreeDTO : childList) {
            orgTreeDTO.setChildren(getChildren(orgTreeDTO.getId(), originData));
        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    @Override
    public List<Long> findAllChildOrg(Long parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        List<Org> originData = orgDAO.select(params);
        return getChildrenFlat(parentId, originData, new ArrayList<>());
    }

    private List<Long> getChildrenFlat(Long parentId, List<Org> orgs, List<Long> result) {

        if (!parentId.equals(0L)) {
            result.add(parentId);
        }
        for (Org org : orgs) {
            Map<String, Object> params = new HashMap<>(1);
            params.put("parentId", org.getId());
            List<Org> children = orgDAO.select(params);
            if (null == children || children.size() == 0) {
                result.add(org.getId());
            } else {
                getChildrenFlat(org.getId(), children, result);
            }
        }
        return result;
    }

    @Override
    public int countByOrgIds(List<Long> ids, Map<String, Object> params) {
        params.put("list", ids);
        return userDAO.countByOrgIds(params);
    }

    @Override
    public List<UserDTO> findByOrgIds(List<Long> ids, Map<String, Object> params,
                                      Vector<SortingContext> scs, PagingContext pc) {

        params.put("list", ids);

        if (params.size() > 0) {
            params = params.entrySet().stream().filter(entry ->
                    (StringUtils.hasLength(entry.getKey()) && null != entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        params.put("pc", pc);
        params.put("scs", scs);

        List<User> users = userDAO.selectByOrgIds(params);
        List<UserDTO> resultList = new ArrayList<>();

        users.forEach(tem -> {
            UserDTO userDTO = new UserDTO();
            BeanUtil.copyProperties(tem, userDTO);
            userDTO.setRoleNames(roleDAO.selectRoleNamesByUserId(userDTO.getId()));
            resultList.add(userDTO);
        });
        return resultList;
    }
}
