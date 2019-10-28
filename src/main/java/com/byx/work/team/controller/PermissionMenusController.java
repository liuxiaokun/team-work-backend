package com.byx.work.team.controller;

import com.byx.work.team.controller.BaseController;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.PermissionMenusDTO;
import com.byx.work.team.model.entity.PermissionMenus;
import com.byx.work.team.service.PermissionMenusService;
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
 * @since 2019/10/28
 */
@RestController
@RequestMapping(value = "/permission/menus", name = "菜单分配表")
@Slf4j
@Api("菜单分配表")
public class PermissionMenusController extends BaseController<PermissionMenus> {

    private final PermissionMenusService permissionMenusService;

    @Autowired
    public PermissionMenusController(PermissionMenusService permissionMenusService) {
        this.permissionMenusService = permissionMenusService;
    }

    @ApiOperation(value = "根据条件查询PermissionMenus列表", notes = "包含查询条件，分页以及排序功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "s", value = "每页的条数", paramType = "query"),
            @ApiImplicitParam(name = "p", value = "请求的页码", paramType = "query"),
            @ApiImplicitParam(name = "sc", value = "排序字段，格式：scs=name(asc),sc=age(desc),有序", paramType = "query"),
            })
    @GetMapping(name = "team-菜单分配表管理")
    public Object list(HttpServletRequest request, PermissionMenusDTO permissionMenusDTO) {
        log.info("list:{}", permissionMenusDTO);

        Map<String, Object> params = getConditionsMap(request);
        List<PermissionMenusDTO> list = new ArrayList<>();
        int total = permissionMenusService.count(params);
        PagingContext pc = getPagingContext(request, total);
        Vector<SortingContext> scs = getSortingContext(request);
        if (total > 0) {
            list = permissionMenusService.find(params, scs, pc);
        }
        return RO.success(list, pc, scs);
    }

    @ApiOperation(value = "查询PermissionMenus", notes = "根据ID查询PermissionMenus")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @GetMapping(value = "/{id}", name = "查看")
    public Object view(@PathVariable("id") Long id) {
        log.info("get permissionMenus Id:{}", id);
        PermissionMenusDTO permissionMenusDTO = permissionMenusService.findPermissionMenusById(id);
        return RO.success(permissionMenusDTO);
    }

    @ApiOperation(value = "新增PermissionMenus", notes = "新增一条PermissionMenus记录")
    @PostMapping(name = "创建")
    public Object create(@RequestBody PermissionMenusDTO permissionMenusDTO, HttpServletRequest request) {
        log.info("add permissionMenus DTO:{}", permissionMenusDTO);
        PermissionMenus sourcePermissionMenus = new PermissionMenus();
        try {
            PermissionMenus permissionMenus = BeanUtil.copyProperties(permissionMenusDTO, sourcePermissionMenus);
            permissionMenusService.savePermissionMenus(this.packAddBaseProps(permissionMenus, request));
        } catch (BizException e) {
            log.error("add permissionMenus failed,  permissionMenusDTO: {}, error message:{}", permissionMenusDTO, e.getMessage());
            return RO.error(e.getMessage());
        }
        return RO.success();
    }

    @ApiOperation(value = "修改PermissionMenus", notes = "根据ID, 修改一条PermissionMenus记录")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @PutMapping(value = "/{id}", name = "修改")
    public Object update(@PathVariable("id") Long id, @RequestBody PermissionMenusDTO permissionMenusDTO, HttpServletRequest request) {
        log.info("put modify id:{}, permissionMenus DTO:{}", id, permissionMenusDTO);
        PermissionMenus permissionMenus = new PermissionMenus();
        permissionMenus.setId(id);
        permissionMenusService.updatePermissionMenus(this.packModifyBaseProps(BeanUtil.copyProperties(permissionMenusDTO, permissionMenus), request));
        return RO.success();
    }

    @ApiOperation(value = "修改PermissionMenus", notes = "根据ID, 部分修改一条PermissionMenus记录")
    @ApiImplicitParam(name = "id", value = "主键ID", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @PatchMapping(value = "/{id}", name = "修改指定项")
    public Object updatex(@PathVariable("id") Long id, HttpServletRequest request, @RequestBody Map<String, Object> params) {
        log.info("Patch modify PermissionMenus Id:{}", id);
        params.put("modified_by", getUserId(request));
        params.put("modified_date", System.currentTimeMillis());

        Map<String, Object> conditions = new HashMap<>(1);
        conditions.put("id", id);
        permissionMenusService.updatePermissionMenusSelective(params, conditions);
        return RO.success();
    }

    @ApiOperation(value = "删除PermissionMenus", notes = "根据ID, 逻辑删除一条PermissionMenus记录")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @DeleteMapping(value = "/{id}", name = "删除")
    public Object remove(HttpServletRequest request, @PathVariable("id") Long id) {
        log.info("delete permissionMenus, id:{}", id);

        try {
            permissionMenusService.logicDeletePermissionMenus(id, this.getUserId(request));
        } catch (BizException e) {
            log.error("delete failed, permissionMenus id: {}, error message:{}", id, e.getMessage());
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
        Double sum = permissionMenusService.sum(sumField, params);
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
        Map<String, Double> result = permissionMenusService.groupSum(groupField, sumField, params);
        return RO.success(result);
    }

    @ApiOperation(value = "按照指定字段分组计算指定字段的数目", notes = "根据指定的字段，用count函数计算符合条件记录字段的数目。")
    @ApiImplicitParam(name = "groupField", value = "分组的字段", dataType = "String", paramType = "path", required = true)
    @GetMapping(value = "/group/{groupField}/count", name = "统计分组字段数")
    public Object groupCount(@PathVariable String groupField, HttpServletRequest request) {
        log.info("groupField:{}", groupField);
        Map<String, Object> params = getConditionsMap(request);
        Map<String, Integer> result = permissionMenusService.groupCount(groupField, params);
        return RO.success(result);
    }
}
