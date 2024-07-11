package com.tiger.web.controller.system;

import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tiger.common.core.domain.model.LoginUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tiger.common.annotation.Log;
import com.tiger.common.core.controller.BaseController;
import com.tiger.common.core.domain.AjaxResult;
import com.tiger.common.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.tiger.system.domain.SysVolunteerOrganizationRelation;
import com.tiger.system.service.ISysVolunteerOrganizationRelationService;
import com.tiger.common.utils.poi.ExcelUtil;
import com.tiger.common.core.page.TableDataInfo;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 志愿者志愿组织关系Controller
 * 
 * @author tiger
 * @date 2024-03-24
 */
@RestController
@RequestMapping("/system/volunteerOrganization/relation")
@Api(tags = " sysVolunteerOrganizationRelation管理")
public class SysVolunteerOrganizationRelationController extends BaseController
{
    @Autowired
    private ISysVolunteerOrganizationRelationService sysVolunteerOrganizationRelationService;

    /**
     * 查询志愿者志愿组织关系列表
     */
    // @PreAuthorize("@ss.hasPermi('system:relation:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询志愿者志愿组织关系列表",notes = "志愿者志愿组织关系列表查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "志愿者与志愿组织关系id", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "organizationId", value = "志愿组织id", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "volunteerId", value = "志愿者id", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "status", value = "志愿者与志愿组织审核状态", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "createTime", value = "关系创建时间", dataType = "Date", dataTypeClass = Date.class),
        @ApiImplicitParam(name = "updateTime", value = "关系更新时间", dataType = "Date", dataTypeClass = Date.class),
    })

    public TableDataInfo list(SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation)
    {
        startPage();
        List<SysVolunteerOrganizationRelation> list = sysVolunteerOrganizationRelationService.selectSysVolunteerOrganizationRelationList(sysVolunteerOrganizationRelation);
        return getDataTable(list);
    }

    /**
     * 导出志愿者志愿组织关系列表
     */
    // @PreAuthorize("@ss.hasPermi('system:relation:export')")
    @Log(title = "志愿者志愿组织关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出志愿者志愿组织关系列表",notes = "导出志愿者志愿组织关系列表")
    public void export(HttpServletResponse response, SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation)
    {
        List<SysVolunteerOrganizationRelation> list = sysVolunteerOrganizationRelationService.selectSysVolunteerOrganizationRelationList(sysVolunteerOrganizationRelation);
        ExcelUtil<SysVolunteerOrganizationRelation> util = new ExcelUtil<SysVolunteerOrganizationRelation>(SysVolunteerOrganizationRelation.class);
        util.exportExcel(response, list, "志愿者志愿组织关系数据");
    }

    /**
     * 获取志愿者志愿组织关系详细信息
     */
    // @PreAuthorize("@ss.hasPermi('system:relation:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取志愿者志愿组织关系详细信息",notes = "获取志愿者志愿组织关系详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "$column.javaField", value = "$column.columnComment", dataType = "id", dataTypeClass = Long.class),
    })
    public AjaxResult getInfo(@Valid @NotNull(message = "id不能为空") @PathVariable("id") Long id)
    {
        return success(sysVolunteerOrganizationRelationService.selectSysVolunteerOrganizationRelationById(id));
    }

    /**
     * 新增志愿者志愿组织关系
     */
    // @PreAuthorize("@ss.hasPermi('system:relation:add')")
    @Log(title = "志愿者志愿组织关系", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增志愿者志愿组织关系",notes = "新增志愿者志愿组织关系")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "志愿者与志愿组织关系id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "organizationId", value = "志愿组织id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "volunteerId", value = "志愿者id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "status", value = "志愿者与志愿组织审核状态", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "createTime", value = "关系创建时间", dataType = "Date", dataTypeClass = Date.class),
                @ApiImplicitParam(name = "updateTime", value = "关系更新时间", dataType = "Date", dataTypeClass = Date.class),
    })
    public AjaxResult add(@Valid @RequestBody SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation)
    {
        LoginUser loginUser = getLoginUser();
        sysVolunteerOrganizationRelation.setUserId(loginUser.getUserId());
        return toAjax(sysVolunteerOrganizationRelationService.insertSysVolunteerOrganizationRelation(sysVolunteerOrganizationRelation));
    }

    /**
     * 修改志愿者志愿组织关系
     */
    // @PreAuthorize("@ss.hasPermi('system:relation:edit')")
    @Log(title = "志愿者志愿组织关系", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改志愿者志愿组织关系",notes = "修改志愿者志愿组织关系")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "志愿者与志愿组织关系id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "organizationId", value = "志愿组织id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "volunteerId", value = "志愿者id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "status", value = "志愿者与志愿组织审核状态", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "createTime", value = "关系创建时间", dataType = "Date", dataTypeClass = Date.class),
                @ApiImplicitParam(name = "updateTime", value = "关系更新时间", dataType = "Date", dataTypeClass = Date.class),
    })
    public AjaxResult edit(@Valid @RequestBody SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation)
    {
        return toAjax(sysVolunteerOrganizationRelationService.updateSysVolunteerOrganizationRelation(sysVolunteerOrganizationRelation));
    }

    /**
     * 删除志愿者志愿组织关系
     */
    // @PreAuthorize("@ss.hasPermi('system:relation:remove')")
    @Log(title = "志愿者志愿组织关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value = "删除志愿者志愿组织关系",notes = "删除志愿者志愿组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "ids", dataTypeClass = Long.class),
    })
    public AjaxResult remove(@Valid @NotEmpty(message = "ids字段不能为空") @PathVariable Long[] ids)
    {
        return toAjax(sysVolunteerOrganizationRelationService.deleteSysVolunteerOrganizationRelationByIds(ids));
    }
}
