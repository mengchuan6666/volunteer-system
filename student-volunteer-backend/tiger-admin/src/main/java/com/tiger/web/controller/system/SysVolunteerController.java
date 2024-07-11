package com.tiger.web.controller.system;

import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
import com.tiger.system.domain.SysVolunteer;
import com.tiger.system.service.ISysVolunteerService;
import com.tiger.common.utils.poi.ExcelUtil;
import com.tiger.common.core.page.TableDataInfo;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 志愿者信息Controller
 * 
 * @author tiger
 * @date 2024-03-24
 */
@RestController
@RequestMapping("/system/volunteer")
@Api(tags = " sysVolunteer管理")
public class SysVolunteerController extends BaseController
{
    @Autowired
    private ISysVolunteerService sysVolunteerService;

    /**
     * 查询志愿者信息列表
     */
    // @PreAuthorize("@ss.hasPermi('system:volunteer:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询志愿者信息列表",notes = "志愿者信息列表查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "$column.columnComment", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "name", value = "志愿者名称", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "sex", value = "志愿者性别", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "contactPhone", value = "联系方式", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "status", value = "志愿者审核状态", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "registerTime", value = "加入志愿者时间", dataType = "Date", dataTypeClass = Date.class),
        @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "Date", dataTypeClass = Date.class),
    })

    public TableDataInfo list(SysVolunteer sysVolunteer)
    {
        startPage();
        List<SysVolunteer> list = sysVolunteerService.selectSysVolunteerList(sysVolunteer);
        return getDataTable(list);
    }

    /**
     * 导出志愿者信息列表
     */
    // @PreAuthorize("@ss.hasPermi('system:volunteer:export')")
    @Log(title = "志愿者信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出志愿者信息列表",notes = "导出志愿者信息列表")
    public void export(HttpServletResponse response, SysVolunteer sysVolunteer)
    {
        List<SysVolunteer> list = sysVolunteerService.selectSysVolunteerList(sysVolunteer);
        ExcelUtil<SysVolunteer> util = new ExcelUtil<SysVolunteer>(SysVolunteer.class);
        util.exportExcel(response, list, "志愿者信息数据");
    }

    /**
     * 获取志愿者信息详细信息
     */
    // @PreAuthorize("@ss.hasPermi('system:volunteer:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取志愿者信息详细信息",notes = "获取志愿者信息详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "$column.javaField", value = "$column.columnComment", dataType = "id", dataTypeClass = Long.class),
    })
    public AjaxResult getInfo(@Valid @NotNull(message = "id不能为空") @PathVariable("id") Long id)
    {
        return success(sysVolunteerService.selectSysVolunteerById(id));
    }

    /**
     * 新增志愿者信息
     */
    // @PreAuthorize("@ss.hasPermi('system:volunteer:add')")
    @Log(title = "志愿者信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增志愿者信息",notes = "新增志愿者信息")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "$column.columnComment", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "name", value = "志愿者名称", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "sex", value = "志愿者性别", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "contactPhone", value = "联系方式", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "status", value = "志愿者审核状态", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "registerTime", value = "加入志愿者时间", dataType = "Date", dataTypeClass = Date.class),
                @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "Date", dataTypeClass = Date.class),
    })
    public AjaxResult add(@Valid @RequestBody SysVolunteer sysVolunteer) throws Exception
    {
        List<SysVolunteer> sysVolunteers = sysVolunteerService.selectSysVolunteerList(sysVolunteer);
        if (!CollectionUtils.isEmpty(sysVolunteers)){
            throw new Exception("该用户已经是志愿者，请勿重复加入");
        }
        return toAjax(sysVolunteerService.insertSysVolunteer(sysVolunteer));
    }

    /**
     * 修改志愿者信息
     */
    // @PreAuthorize("@ss.hasPermi('system:volunteer:edit')")
    @Log(title = "志愿者信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改志愿者信息",notes = "修改志愿者信息")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "$column.columnComment", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "name", value = "志愿者名称", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "sex", value = "志愿者性别", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "contactPhone", value = "联系方式", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "status", value = "志愿者审核状态", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "registerTime", value = "加入志愿者时间", dataType = "Date", dataTypeClass = Date.class),
                @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "Date", dataTypeClass = Date.class),
    })
    public AjaxResult edit(@Valid @RequestBody SysVolunteer sysVolunteer)
    {
        return toAjax(sysVolunteerService.updateSysVolunteer(sysVolunteer));
    }

    /**
     * 删除志愿者信息
     */
    // @PreAuthorize("@ss.hasPermi('system:volunteer:remove')")
    @Log(title = "志愿者信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value = "删除志愿者信息",notes = "删除志愿者信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "ids", dataTypeClass = Long.class),
    })
    public AjaxResult remove(@Valid @NotEmpty(message = "ids字段不能为空") @PathVariable Long[] ids)
    {
        return toAjax(sysVolunteerService.deleteSysVolunteerByIds(ids));
    }
}
