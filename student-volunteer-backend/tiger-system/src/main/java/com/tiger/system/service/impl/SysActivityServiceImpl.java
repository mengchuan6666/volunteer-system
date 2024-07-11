package com.tiger.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiger.common.utils.DateUtils;
import com.tiger.system.domain.vo.SysActivityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.system.mapper.SysActivityMapper;
import com.tiger.system.domain.SysActivity;
import com.tiger.system.service.ISysActivityService;
/**
 * 活动信息Service业务层处理
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Service
public class SysActivityServiceImpl extends ServiceImpl<SysActivityMapper, SysActivity> implements ISysActivityService
{
    @Autowired
    private SysActivityMapper sysActivityMapper;

    /**
     * 查询活动信息
     * 
     * @param id 活动信息主键
     * @return 活动信息
     */
    @Override
    public SysActivity selectSysActivityById(Long id)
    {
        return sysActivityMapper.selectSysActivityById(id);
    }

    /**
     * 查询活动信息列表
     * 
     * @param sysActivity 活动信息
     * @return 活动信息
     */
    @Override
    public List<SysActivityDto> selectSysActivityList(SysActivity sysActivity)
    {
        return sysActivityMapper.selectSysActivityList(sysActivity);
    }

    /**
     * 新增活动信息
     * 
     * @param sysActivity 活动信息
     * @return 结果
     */
    @Override
    public int insertSysActivity(SysActivity sysActivity)
    {
        sysActivity.setCreateTime(DateUtils.getNowDate());
        return sysActivityMapper.insertSysActivity(sysActivity);
    }

    /**
     * 修改活动信息
     * 
     * @param sysActivity 活动信息
     * @return 结果
     */
    @Override
    public int updateSysActivity(SysActivity sysActivity)
    {
        sysActivity.setUpdateTime(DateUtils.getNowDate());
        return sysActivityMapper.updateSysActivity(sysActivity);
    }

    /**
     * 批量删除活动信息
     * 
     * @param ids 需要删除的活动信息主键
     * @return 结果
     */
    @Override
    public int deleteSysActivityByIds(Long[] ids)
    {
        return sysActivityMapper.deleteSysActivityByIds(ids);
    }

    /**
     * 删除活动信息信息
     * 
     * @param id 活动信息主键
     * @return 结果
     */
    @Override
    public int deleteSysActivityById(Long id)
    {
        return sysActivityMapper.deleteSysActivityById(id);
    }
}
