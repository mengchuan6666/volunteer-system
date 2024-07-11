package com.tiger.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiger.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.system.mapper.SysVolunteerMapper;
import com.tiger.system.domain.SysVolunteer;
import com.tiger.system.service.ISysVolunteerService;
/**
 * 志愿者信息Service业务层处理
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Service
public class SysVolunteerServiceImpl extends ServiceImpl<SysVolunteerMapper, SysVolunteer> implements ISysVolunteerService
{
    @Autowired
    private SysVolunteerMapper sysVolunteerMapper;

    /**
     * 查询志愿者信息
     * 
     * @param id 志愿者信息主键
     * @return 志愿者信息
     */
    @Override
    public SysVolunteer selectSysVolunteerById(Long id)
    {
        return sysVolunteerMapper.selectSysVolunteerById(id);
    }

    /**
     * 查询志愿者信息列表
     * 
     * @param sysVolunteer 志愿者信息
     * @return 志愿者信息
     */
    @Override
    public List<SysVolunteer> selectSysVolunteerList(SysVolunteer sysVolunteer)
    {
        return sysVolunteerMapper.selectSysVolunteerList(sysVolunteer);
    }

    /**
     * 新增志愿者信息
     * 
     * @param sysVolunteer 志愿者信息
     * @return 结果
     */
    @Override
    public int insertSysVolunteer(SysVolunteer sysVolunteer)
    {
        return sysVolunteerMapper.insertSysVolunteer(sysVolunteer);
    }

    /**
     * 修改志愿者信息
     * 
     * @param sysVolunteer 志愿者信息
     * @return 结果
     */
    @Override
    public int updateSysVolunteer(SysVolunteer sysVolunteer)
    {
        sysVolunteer.setUpdateTime(DateUtils.getNowDate());
        return sysVolunteerMapper.updateSysVolunteer(sysVolunteer);
    }

    /**
     * 批量删除志愿者信息
     * 
     * @param ids 需要删除的志愿者信息主键
     * @return 结果
     */
    @Override
    public int deleteSysVolunteerByIds(Long[] ids)
    {
        return sysVolunteerMapper.deleteSysVolunteerByIds(ids);
    }

    /**
     * 删除志愿者信息信息
     * 
     * @param id 志愿者信息主键
     * @return 结果
     */
    @Override
    public int deleteSysVolunteerById(Long id)
    {
        return sysVolunteerMapper.deleteSysVolunteerById(id);
    }
}
