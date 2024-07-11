package com.tiger.system.mapper;

import java.util.List;
import com.tiger.system.domain.SysVolunteerOrganizationRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 志愿者志愿组织关系Mapper接口
 * 
 * @author tiger
 * @date 2024-03-24
 */
public interface SysVolunteerOrganizationRelationMapper extends BaseMapper<SysVolunteerOrganizationRelation> {
    /**
     * 查询志愿者志愿组织关系
     *
     * @param id 志愿者志愿组织关系主键
     * @return 志愿者志愿组织关系
     */
    SysVolunteerOrganizationRelation selectSysVolunteerOrganizationRelationById(Long id);

    /**
     * 查询志愿者志愿组织关系列表
     *
     * @param sysVolunteerOrganizationRelation 志愿者志愿组织关系
     * @return 志愿者志愿组织关系集合
     */
    List<SysVolunteerOrganizationRelation> selectSysVolunteerOrganizationRelationList(SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation);

    /**
     * 新增志愿者志愿组织关系
     *
     * @param sysVolunteerOrganizationRelation 志愿者志愿组织关系
     * @return 结果
     */
    int insertSysVolunteerOrganizationRelation(SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation);

    /**
     * 修改志愿者志愿组织关系
     *
     * @param sysVolunteerOrganizationRelation 志愿者志愿组织关系
     * @return 结果
     */
    int updateSysVolunteerOrganizationRelation(SysVolunteerOrganizationRelation sysVolunteerOrganizationRelation);

    /**
     * 删除志愿者志愿组织关系
     *
     * @param id 志愿者志愿组织关系主键
     * @return 结果
     */
    int deleteSysVolunteerOrganizationRelationById(Long id);

    /**
     * 批量删除志愿者志愿组织关系
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSysVolunteerOrganizationRelationByIds(Long[] ids);
}
