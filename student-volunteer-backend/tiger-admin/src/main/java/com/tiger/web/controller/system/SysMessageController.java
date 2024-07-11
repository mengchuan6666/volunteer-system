package com.tiger.web.controller.system;

import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
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
import com.tiger.system.domain.SysMessage;
import com.tiger.system.service.ISysMessageService;
import com.tiger.common.utils.poi.ExcelUtil;
import com.tiger.common.core.page.TableDataInfo;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 留言板信息Controller
 * 
 * @author tiger
 * @date 2024-03-24
 */
@RestController
@RequestMapping("/system/message")
@Api(tags = " sysMessage管理")
public class SysMessageController extends BaseController
{
    @Autowired
    private ISysMessageService sysMessageService;

    /**
     * 查询留言板信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:message:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询留言板信息列表",notes = "留言板信息列表查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "留言id", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "conent", value = "留言内容", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "status", value = "留言处理状态", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "answer", value = "留言回复", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "createTime", value = "留言时间", dataType = "Date", dataTypeClass = Date.class),
        @ApiImplicitParam(name = "updateTime", value = "$column.columnComment", dataType = "Date", dataTypeClass = Date.class),
    })

    public TableDataInfo list(SysMessage sysMessage)
    {
        startPage();
        List<SysMessage> list = sysMessageService.selectSysMessageList(sysMessage);
        return getDataTable(list);
    }

    /**
     * 导出留言板信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:message:export')")
    @Log(title = "留言板信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出留言板信息列表",notes = "导出留言板信息列表")
    public void export(HttpServletResponse response, SysMessage sysMessage)
    {
        List<SysMessage> list = sysMessageService.selectSysMessageList(sysMessage);
        ExcelUtil<SysMessage> util = new ExcelUtil<SysMessage>(SysMessage.class);
        util.exportExcel(response, list, "留言板信息数据");
    }

    /**
     * 获取留言板信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:message:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取留言板信息详细信息",notes = "获取留言板信息详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "$column.javaField", value = "$column.columnComment", dataType = "id", dataTypeClass = Long.class),
    })
    public AjaxResult getInfo(@Valid @NotNull(message = "id不能为空") @PathVariable("id") Long id)
    {
        return success(sysMessageService.selectSysMessageById(id));
    }

    /**
     * 新增留言板信息
     */
    @PreAuthorize("@ss.hasPermi('system:message:add')")
    @Log(title = "留言板信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增留言板信息",notes = "新增留言板信息")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "留言id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "conent", value = "留言内容", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "status", value = "留言处理状态", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "answer", value = "留言回复", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "createTime", value = "留言时间", dataType = "Date", dataTypeClass = Date.class),
                @ApiImplicitParam(name = "updateTime", value = "$column.columnComment", dataType = "Date", dataTypeClass = Date.class),
    })
    public AjaxResult add(@Valid @RequestBody SysMessage sysMessage)
    {
        return toAjax(sysMessageService.insertSysMessage(sysMessage));
    }

    /**
     * 修改留言板信息
     */
    @PreAuthorize("@ss.hasPermi('system:message:edit')")
    @Log(title = "留言板信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改留言板信息",notes = "修改留言板信息")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "留言id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", dataTypeClass = Long.class),
                @ApiImplicitParam(name = "conent", value = "留言内容", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "status", value = "留言处理状态", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "answer", value = "留言回复", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "extra", value = "扩展字段", dataType = "String", dataTypeClass = String.class),
                @ApiImplicitParam(name = "createTime", value = "留言时间", dataType = "Date", dataTypeClass = Date.class),
                @ApiImplicitParam(name = "updateTime", value = "$column.columnComment", dataType = "Date", dataTypeClass = Date.class),
    })
    public AjaxResult edit(@Valid @RequestBody SysMessage sysMessage)
    {
        return toAjax(sysMessageService.updateSysMessage(sysMessage));
    }

    /**
     * 删除留言板信息
     */
    @PreAuthorize("@ss.hasPermi('system:message:remove')")
    @Log(title = "留言板信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value = "删除留言板信息",notes = "删除留言板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", dataType = "ids", dataTypeClass = Long.class),
    })
    public AjaxResult remove(@Valid @NotEmpty(message = "ids字段不能为空") @PathVariable Long[] ids)
    {
        return toAjax(sysMessageService.deleteSysMessageByIds(ids));
    }
}
