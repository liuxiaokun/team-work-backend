package com.byx.work.team.controller;

import com.byx.work.team.controller.BaseController;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.OrgDTO;
import com.byx.work.team.model.dto.UserDTO;
import com.byx.work.team.model.entity.Org;
import com.byx.work.team.service.OrgService;
import com.byx.work.team.utils.BeanUtil;
import com.byx.framework.core.domain.PagingContext;
import com.byx.framework.core.domain.SortingContext;
import com.byx.framework.core.web.RO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/11/01
 */
@RestController
@RequestMapping(value = "/org", name = "组织机构表")
@Slf4j
@Api("组织机构表")
public class OrgController extends BaseController<Org> {

    private final OrgService orgService;

    @Autowired
    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @ApiOperation(value = "根据条件查询Org列表", notes = "包含查询条件，分页以及排序功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "s", value = "每页的条数", paramType = "query"),
            @ApiImplicitParam(name = "p", value = "请求的页码", paramType = "query"),
            @ApiImplicitParam(name = "sc", value = "排序字段，格式：scs=name(asc),sc=age(desc),有序", paramType = "query"),
            })
    @GetMapping(name = "team-组织机构表管理")
    public Object list(HttpServletRequest request, OrgDTO orgDTO) {
        log.info("list:{}", orgDTO);

        Map<String, Object> params = getConditionsMap(request);
        List<OrgDTO> list = new ArrayList<>();
        int total = orgService.count(params);
        PagingContext pc = getPagingContext(request, total);
        Vector<SortingContext> scs = getSortingContext(request);
        if (total > 0) {
            list = orgService.find(params, scs, pc);
        }
        return RO.success(list, pc, scs);
    }

    @ApiOperation(value = "查询Org", notes = "根据ID查询Org")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @GetMapping(value = "/{id}", name = "查看")
    public Object view(@PathVariable("id") Long id) {
        log.info("get org Id:{}", id);
        OrgDTO orgDTO = orgService.findOrgById(id);
        return RO.success(orgDTO);
    }

    @ApiOperation(value = "新增Org", notes = "新增一条Org记录")
    @PostMapping(name = "创建")
    public Object create(@RequestBody OrgDTO orgDTO, HttpServletRequest request) {
        log.info("add org DTO:{}", orgDTO);
        Org sourceOrg = new Org();
        try {
            Org org = BeanUtil.copyProperties(orgDTO, sourceOrg);
            orgService.saveOrg(this.packAddBaseProps(org, request));
        } catch (BizException e) {
            log.error("add org failed,  orgDTO: {}, error message:{}", orgDTO, e.getMessage());
            return RO.error(e.getMessage());
        }
        return RO.success();
    }

    @ApiOperation(value = "修改Org", notes = "根据ID, 修改一条Org记录")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @PutMapping(value = "/{id}", name = "修改")
    public Object update(@PathVariable("id") Long id, @RequestBody OrgDTO orgDTO, HttpServletRequest request) {
        log.info("put modify id:{}, org DTO:{}", id, orgDTO);
        Org org = new Org();
        org.setId(id);
        orgService.updateOrg(this.packModifyBaseProps(BeanUtil.copyProperties(orgDTO, org), request));
        return RO.success();
    }

    @ApiOperation(value = "修改Org", notes = "根据ID, 部分修改一条Org记录")
    @ApiImplicitParam(name = "id", value = "主键ID", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @PatchMapping(value = "/{id}", name = "修改指定项")
    public Object updatex(@PathVariable("id") Long id, HttpServletRequest request, @RequestBody Map<String, Object> params) {
        log.info("Patch modify Org Id:{}", id);
        params.put("modified_by", getUserId(request));
        params.put("modified_date", System.currentTimeMillis());

        Map<String, Object> conditions = new HashMap<>(1);
        conditions.put("id", id);
        orgService.updateOrgSelective(params, conditions);
        return RO.success();
    }

    @ApiOperation(value = "删除Org", notes = "根据ID, 逻辑删除一条Org记录")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @DeleteMapping(value = "/{id}", name = "删除")
    public Object remove(HttpServletRequest request, @PathVariable("id") Long id) {
        log.info("delete org, id:{}", id);

        try {
            orgService.logicDeleteOrg(id, this.getUserId(request));
        } catch (BizException e) {
            log.error("delete failed, org id: {}, error message:{}", id, e.getMessage());
            return RO.error(e.getMessage());
        }
        return RO.success();
    }

    @ApiOperation(value = "计算指定字段的和", notes = "用sum函数计算符合条件记录的指定字段的和。")
    @ApiImplicitParam(name = "sumField", value = "求和的字段", dataType = "String", paramType = "path", required = true)
    @GetMapping(value = "/sum/{sumField}", name = "计算指定字段的和")
    public Object sum(@PathVariable String sumField, HttpServletRequest request) {
        log.info("sumField:{}", sumField);
        Map<String, Object> params = getConditionsMap(request);
        Double sum = orgService.sum(sumField, params);
        return RO.success(sum);
    }

    @ApiOperation(value = "按照指定字段分组并计算指定字段的和", notes = "根据指定的字段分组，然后用sum函数计算符合条件记录指定字段的和。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sumField", value = "计算字段", dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "groupField", value = "分组字段", dataType = "String", paramType = "path", required = true)
    })
    @GetMapping(value = "/group/{groupField}/sum/{sumField}", name = "计算分组字段和")
    public Object groupSum(@PathVariable String groupField, @PathVariable String sumField, HttpServletRequest request) {
        log.info("groupField:{}, sumField:{}", groupField, sumField);
        Map<String, Object> params = getConditionsMap(request);
        Map<String, Double> result = orgService.groupSum(groupField, sumField, params);
        return RO.success(result);
    }

    @ApiOperation(value = "按照指定字段分组计算指定字段的数目", notes = "根据指定的字段，用count函数计算符合条件记录字段的数目。")
    @ApiImplicitParam(name = "groupField", value = "分组的字段", dataType = "String", paramType = "path", required = true)
    @GetMapping(value = "/group/{groupField}/count", name = "统计分组字段数")
    public Object groupCount(@PathVariable String groupField, HttpServletRequest request) {
        log.info("groupField:{}", groupField);
        Map<String, Object> params = getConditionsMap(request);
        Map<String, Integer> result = orgService.groupCount(groupField, params);
        return RO.success(result);
    }

    @GetMapping(value = "/tree", name = "树级子组织")
    public Object tree(Long parentId, HttpServletRequest request) {
        Map<String, Object> params = getConditionsMap(request);
        log.info("tree parentId:{}", parentId);
        if (null == parentId) {
            parentId = 0L;
        }
        return RO.success(orgService.tree(parentId,params));
    }

    @GetMapping(value = "/{orgId}/user", name = "组织下的用户")
    public Object findOrgUsers(HttpServletRequest request, @PathVariable Long orgId) {
        Map<String, Object> params = getConditionsMap(request);
        List<Long> ids = orgService.findAllChildOrg(orgId);
        int total = orgService.countByOrgIds(ids, params);
        PagingContext pc = getPagingContext(request, total);
        Vector<SortingContext> scs = getSortingContext(request);
        List<UserDTO> users = new ArrayList<>();
        if (total > 0) {
            users = orgService.findByOrgIds(ids, params, scs, pc);
        }
        return RO.success(users, pc, scs);
    }
}
