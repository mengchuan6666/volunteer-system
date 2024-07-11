package com.tiger.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiger.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.system.mapper.SysVolunteerOrganizationRelationMapper;
import com.tiger.system.domain.SysVolunteerOrganizationRelation;
import com.tiger.system.service.ISysVolunteerOrganizationRelationService;
/**
 * 志愿者志愿组织关系Service业务层处理
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Service
public class SysVolunteerOrganizationRelationServiceImpl extends ServiceImpl<SysVolunteerOrganizationRelationMapper, SysVolunteerOrganizationRelation> implements ISysVolunteerOrganizationRelationService
{
    @Autowired
    private SysVolunteerOrganizationRelationMapper sysVolunteerOrganizationRelationMapper;

    /**
     * 查询志愿者志愿组织关系
     * 
     * @param id 志愿者志愿组织关系主键
     * @return 志愿者志愿组织关系
     */
    @Override
    public SysVolunteerOrganizationRelation selectSysVolunteerOrganizationRelationById(Long id)
    {
        return sysVolunteerOrganizationRelationMapper.selectSysVolunteerOrganizationRelationById(id);
    }

    /**
     * 查询志愿者志愿组织关系列表
     * 
     * @param sysVolunteerOrganizationRelation 志愿者志愿组织关系
     * @return 志愿者志愿组织关系
     */
    @Override
    public List<SysVolunteerOrganizationRelation> selectSysVolunteerOrganizationRelationList(SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation)
    {
        return sysVolunteerOrganizationRelationMapper.selectSysVolunteerOrganizationRelationList(sysVolunteerOrganizationRelation);
    }

    /**
     * 新增志愿者志愿组织关系
     * 
     * @param sysVolunteerOrganizationRelation 志愿者志愿组织关系
     * @return 结果
     */
    @Override
    public int insertSysVolunteerOrganizationRelation(SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation)
    {
        sysVolunteerOrganizationRelation.setCreateTime(DateUtils.getNowDate());
        return sysVolunteerOrganizationRelationMapper.insertSysVolunteerOrganizationRelation(sysVolunteerOrganizationRelation);
    }

    /**
     * 修改志愿者志愿组织关系
     * 
     * @param sysVolunteerOrganizationRelation 志愿者志愿组织关系
     * @return 结果
     */
    @Override
    public int updateSysVolunteerOrganizationRelation(SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation)
    {
        sysVolunteerOrganizationRelation.setUpdateTime(DateUtils.getNowDate());
        return sysVolunteerOrganizationRelationMapper.updateSysVolunteerOrganizationRelation(sysVolunteerOrganizationRelation);
    }

    /**
     * 批量删除志愿者志愿组织关系
     * 
     * @param ids 需要删除的志愿者志愿组织关系主键
     * @return 结果
     */
    @Override
    public int deleteSysVolunteerOrganizationRelationByIds(Long[] ids)
    {
        return sysVolunteerOrganizationRelationMapper.deleteSysVolunteerOrganizationRelationByIds(ids);
    }

    /**
     * 删除志愿者志愿组织关系信息
     * 
     * @param id 志愿者志愿组织关系主键
     * @return 结果
     */
    @Override
    public int deleteSysVolunteerOrganizationRelationById(Long id)
    {
        return sysVolunteerOrganizationRelationMapper.deleteSysVolunteerOrganizationRelationById(id);
    }
}
