package com.byx.work.team.controller;

import com.byx.work.team.controller.BaseController;
import com.byx.work.team.exception.BizException;
import com.byx.work.team.model.dto.ProjectCascadeDTO;
import com.byx.work.team.model.dto.ProjectDTO;
import com.byx.work.team.model.entity.Project;
import com.byx.work.team.service.ProjectService;
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
@RequestMapping(value = "/project", name = "Project")
@Slf4j
@Api("Project")
public class ProjectController extends BaseController<Project> {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ApiOperation(value = "根据条件查询Project列表", notes = "包含查询条件，分页以及排序功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "s", value = "每页的条数", paramType = "query"),
            @ApiImplicitParam(name = "p", value = "请求的页码", paramType = "query"),
            @ApiImplicitParam(name = "sc", value = "排序字段，格式：scs=name(asc),sc=age(desc),有序", paramType = "query"),
            })
    @GetMapping(name = "team-Project管理")
    public Object list(HttpServletRequest request, ProjectDTO projectDTO) {
        log.info("list:{}", projectDTO);

        Map<String, Object> params = getConditionsMap(request);
        List<ProjectDTO> list = new ArrayList<>();
        int total = projectService.count(params);
        PagingContext pc = getPagingContext(request, total);
        Vector<SortingContext> scs = getSortingContext(request);
        if (total > 0) {
            list = projectService.find(params, scs, pc);
        }
        return RO.success(list, pc, scs);
    }

    @GetMapping(name = "team-Project管理", value = "cascade")
    public Object cascade(HttpServletRequest request, ProjectDTO projectDTO) {
        log.info("cascade:{}", projectDTO);

        Map<String, Object> params = getConditionsMap(request);
        List<ProjectCascadeDTO> projectCascadeDTOS = projectService.find(params);
        return RO.success(projectCascadeDTOS);
    }

    @ApiOperation(value = "查询Project", notes = "根据ID查询Project")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @GetMapping(value = "/{id}", name = "查看")
    public Object view(@PathVariable("id") Long id) {
        log.info("get project Id:{}", id);
        ProjectDTO projectDTO = projectService.findProjectById(id);
        return RO.success(projectDTO);
    }

    @ApiOperation(value = "新增Project", notes = "新增一条Project记录")
    @PostMapping(name = "创建")
    public Object create(@RequestBody ProjectDTO projectDTO, HttpServletRequest request) {
        log.info("add project DTO:{}", projectDTO);
        Project sourceProject = new Project();
        try {
            Project project = BeanUtil.copyProperties(projectDTO, sourceProject);
            projectService.saveProject(this.packAddBaseProps(project, request));
        } catch (BizException e) {
            log.error("add project failed,  projectDTO: {}, error message:{}", projectDTO, e.getMessage());
            return RO.error(e.getMessage());
        }
        return RO.success();
    }

    @ApiOperation(value = "修改Project", notes = "根据ID, 修改一条Project记录")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @PutMapping(value = "/{id}", name = "修改")
    public Object update(@PathVariable("id") Long id, @RequestBody ProjectDTO projectDTO, HttpServletRequest request) {
        log.info("put modify id:{}, project DTO:{}", id, projectDTO);
        Project project = new Project();
        project.setId(id);
        projectService.updateProject(this.packModifyBaseProps(BeanUtil.copyProperties(projectDTO, project), request));
        return RO.success();
    }

    @ApiOperation(value = "修改Project", notes = "根据ID, 部分修改一条Project记录")
    @ApiImplicitParam(name = "id", value = "主键ID", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @PatchMapping(value = "/{id}", name = "修改指定项")
    public Object updatex(@PathVariable("id") Long id, HttpServletRequest request, @RequestBody Map<String, Object> params) {
        log.info("Patch modify Project Id:{}", id);
        params.put("modified_by", getUserId(request));
        params.put("modified_date", System.currentTimeMillis());

        Map<String, Object> conditions = new HashMap<>(1);
        conditions.put("id", id);
        projectService.updateProjectSelective(params, conditions);
        return RO.success();
    }

    @ApiOperation(value = "删除Project", notes = "根据ID, 逻辑删除一条Project记录")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Long", paramType = "path", required = true, example = "947153698855845888")
    @DeleteMapping(value = "/{id}", name = "删除")
    public Object remove(HttpServletRequest request, @PathVariable("id") Long id) {
        log.info("delete project, id:{}", id);

        try {
            projectService.logicDeleteProject(id, this.getUserId(request));
        } catch (BizException e) {
            log.error("delete failed, project id: {}, error message:{}", id, e.getMessage());
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
        Double sum = projectService.sum(sumField, params);
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
        Map<String, Double> result = projectService.groupSum(groupField, sumField, params);
        return RO.success(result);
    }

    @ApiOperation(value = "按照指定字段分组计算指定字段的数目", notes = "根据指定的字段，用count函数计算符合条件记录字段的数目。")
    @ApiImplicitParam(name = "groupField", value = "分组的字段", dataType = "String", paramType = "path", required = true)
    @GetMapping(value = "/group/{groupField}/count", name = "统计分组字段数")
    public Object groupCount(@PathVariable String groupField, HttpServletRequest request) {
        log.info("groupField:{}", groupField);
        Map<String, Object> params = getConditionsMap(request);
        Map<String, Integer> result = projectService.groupCount(groupField, params);
        return RO.success(result);
    }
}
