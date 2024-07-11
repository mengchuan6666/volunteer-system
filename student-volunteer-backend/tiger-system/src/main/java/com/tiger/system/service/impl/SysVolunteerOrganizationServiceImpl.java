package com.tiger.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiger.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.system.mapper.SysVolunteerOrganizationMapper;
import com.tiger.system.domain.SysVolunteerOrganization;
import com.tiger.system.service.ISysVolunteerOrganizationService;
/**
 * 志愿组织信息Service业务层处理
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Service
public class SysVolunteerOrganizationServiceImpl extends ServiceImpl<SysVolunteerOrganizationMapper, SysVolunteerOrganization> implements ISysVolunteerOrganizationService
{
    @Autowired
    private SysVolunteerOrganizationMapper sysVolunteerOrganizationMapper;

    /**
     * 查询志愿组织信息
     * 
     * @param id 志愿组织信息主键
     * @return 志愿组织信息
     */
    @Override
    public SysVolunteerOrganization selectSysVolunteerOrganizationById(Long id)
    {
        return sysVolunteerOrganizationMapper.selectSysVolunteerOrganizationById(id);
    }

    /**
     * 查询志愿组织信息列表
     * 
     * @param sysVolunteerOrganization 志愿组织信息
     * @return 志愿组织信息
     */
    @Override
    public List<SysVolunteerOrganization> selectSysVolunteerOrganizationList(SysVolunteerOrganization sysVolunteerOrganization)
    {
        return sysVolunteerOrganizationMapper.selectSysVolunteerOrganizationList(sysVolunteerOrganization);
    }

    /**
     * 新增志愿组织信息
     * 
     * @param sysVolunteerOrganization 志愿组织信息
     * @return 结果
     */
    @Override
    public int insertSysVolunteerOrganization(SysVolunteerOrganization sysVolunteerOrganization)
    {
        sysVolunteerOrganization.setCreateTime(DateUtils.getNowDate());
        return sysVolunteerOrganizationMapper.insertSysVolunteerOrganization(sysVolunteerOrganization);
    }

    /**
     * 修改志愿组织信息
     * 
     * @param sysVolunteerOrganization 志愿组织信息
     * @return 结果
     */
    @Override
    public int updateSysVolunteerOrganization(SysVolunteerOrganization sysVolunteerOrganization)
    {
        sysVolunteerOrganization.setUpdateTime(DateUtils.getNowDate());
        return sysVolunteerOrganizationMapper.updateSysVolunteerOrganization(sysVolunteerOrganization);
    }

    /**
     * 批量删除志愿组织信息
     * 
     * @param ids 需要删除的志愿组织信息主键
     * @return 结果
     */
    @Override
    public int deleteSysVolunteerOrganizationByIds(Long[] ids)
    {
        return sysVolunteerOrganizationMapper.deleteSysVolunteerOrganizationByIds(ids);
    }

    /**
     * 删除志愿组织信息信息
     * 
     * @param id 志愿组织信息主键
     * @return 结果
     */
    @Override
    public int deleteSysVolunteerOrganizationById(Long id)
    {
        return sysVolunteerOrganizationMapper.deleteSysVolunteerOrganizationById(id);
    }
}
