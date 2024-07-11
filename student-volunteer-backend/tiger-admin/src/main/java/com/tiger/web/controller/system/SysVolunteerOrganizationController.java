package com.tiger.web.controller.system;

import java.util.List;
import java.util.Date;
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
import com.tiger.system.domain.SysVolunteerOrganization;
import com.tiger.system.service.ISysVolunteerOrganizationService;
import com.tiger.common.utils.poi.ExcelUtil;
import com.tiger.common.core.page.TableDataInfo;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 志愿组织信息Controller
 * 
 * @author tiger
 * @date 2024-03-24
 */
@RestController
@RequestMapping("/system/organization")
@Api(tags = " sysVolunteerOrganization管理")
public class SysVolunteerOrganizationController extends BaseController
{
    @Autowired
    private ISysVolunteerOrganizationService sysVolunteerOrganizationService;

    /**
     * 查询志愿组织信息列表
     */
    // @PreAuthorize("@ss.hasPermi('system:organization:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询志愿组织信息列表",notes = "志愿组织信息列表查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "组织id", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "name", value = "组织名", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "introduce", value = "组织介绍", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "createTime", value = "组织创建时间", dataType = "Date", dataTypeClass = Date.class),
        @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "Date", dataTypeClass = Date.class),
    })

    public TableDataInfo list(SysVolunteerOrganization sysVolunteerOrganization)
    {

        // LoginUser loginUser = getLoginUser();
        // if (!loginUser.getUsername().contains("admin")){
        //     sysVolunteerOrganization.setUserId(loginUser.getUserId());
        // }
        startPage();
        List<SysVolunteerOrganization> list = sysVolunteerOrganizationService.selectSysVolunteerOrganizationList(sysVolunteerOrganization);
        return getDataTable(list);
    }

    /**
     * 导出志愿组织信息列表
     */
    // @PreAuthorize("@ss.hasPermi('system:organization:export')")
    @Log(title = "志愿组织信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出志愿组织信息列表",notes = "导出志愿组织信息列表")
    public void export(HttpServletResponse response, SysVolunteerOrganization sysVolunteerOrganization)
    {
        List<SysVolunteerOrganization> list = sysVolunteerOrganizationService.selectSysVolunteerOrganizationList(sysVolunteerOrganization);
        ExcelUtil<SysVolunteerOrganization> util = new ExcelUtil<SysVolunteerOrganization>(SysVolunteerOrganization.class);
        util.exportExcel(response, list, "志愿组织信息数据");
    }

    /**
     * 获取志愿组织信息详细信息
     */
    // @PreAuthorize("@ss.hasPermi('system:organization:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取志愿组织信息详细信息",notes = "获取志愿组织信息详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "$column.javaField", value = "$column.columnComment", dataType = "id", dataTypeClass = Long.class),
    })
    public AjaxResult getInfo(@Valid @NotNull(message = "id不能为空") @PathVariable("id") Long id)
    {
        return success(sysVolunteerOrganizationService.selectSysVolunteerOrganizationById(id));
    }

    /**
     * 新增志愿组织信息
     */
    // @PreAuthorize("@ss.hasPermi('system:organization:add')")
    @Log(title = "志愿组织信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增志愿组织信息",notes = "新增志愿组织信息")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "组织id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "name", value = "组织名", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "introduce", value = "组织介绍", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "createTime", value = "组织创建时间", dataType = "Date", dataTypeClass = Date.class),
                @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "Date", dataTypeClass = Date.class),
    })
    public AjaxResult add(@Valid @RequestBody SysVolunteerOrganization sysVolunteerOrganization)
    {
        return toAjax(sysVolunteerOrganizationService.insertSysVolunteerOrganization(sysVolunteerOrganization));
    }

    /**
     * 修改志愿组织信息
     */
    // @PreAuthorize("@ss.hasPermi('system:organization:edit')")
    @Log(title = "志愿组织信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改志愿组织信息",notes = "修改志愿组织信息")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "组织id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "name", value = "组织名", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "introduce", value = "组织介绍", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "createTime", value = "组织创建时间", dataType = "Date", dataTypeClass = Date.class),
                @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "Date", dataTypeClass = Date.class),
    })
    public AjaxResult edit(@Valid @RequestBody SysVolunteerOrganization sysVolunteerOrganization)
    {
        return toAjax(sysVolunteerOrganizationService.updateSysVolunteerOrganization(sysVolunteerOrganization));
    }

    /**
     * 删除志愿组织信息
     */
    // @PreAuthorize("@ss.hasPermi('system:organization:remove')")
    @Log(title = "志愿组织信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value = "删除志愿组织信息",notes = "删除志愿组织信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "ids", dataTypeClass = Long.class),
    })
    public AjaxResult remove(@Valid @NotEmpty(message = "ids字段不能为空") @PathVariable Long[] ids)
    {
        return toAjax(sysVolunteerOrganizationService.deleteSysVolunteerOrganizationByIds(ids));
    }
}
