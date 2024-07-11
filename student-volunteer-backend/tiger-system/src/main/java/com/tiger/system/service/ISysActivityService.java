package com.tiger.system.service;

import java.util.List;
import com.tiger.system.domain.SysActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiger.system.domain.vo.SysActivityDto;

/**
 * 活动信息Service接口
 * 
 * @author tiger
 * @date 2024-03-24
 */
public interface ISysActivityService extends IService<SysActivity> {
    /**
     * 查询活动信息
     * 
     * @param id 活动信息主键
     * @return 活动信息
     */
    SysActivity selectSysActivityById(Long id);

    /**
     * 查询活动信息列表
     * 
     * @param sysActivity 活动信息
     * @return 活动信息集合
     */
    List<SysActivityDto> selectSysActivityList(SysActivity sysActivity);

    /**
     * 新增活动信息
     * 
     * @param sysActivity 活动信息
     * @return 结果
     */
    int insertSysActivity(SysActivity sysActivity);

    /**
     * 修改活动信息
     * 
     * @param sysActivity 活动信息
     * @return 结果
     */
    int updateSysActivity(SysActivity sysActivity);

    /**
     * 批量删除活动信息
     * 
     * @param ids 需要删除的活动信息主键集合
     * @return 结果
     */
    int deleteSysActivityByIds(Long[] ids);

    /**
     * 删除活动信息信息
     * 
     * @param id 活动信息主键
     * @return 结果
     */
    int deleteSysActivityById(Long id);
}