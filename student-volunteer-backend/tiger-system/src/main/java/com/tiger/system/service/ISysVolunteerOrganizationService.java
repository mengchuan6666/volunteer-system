package com.tiger.system.service;

import java.util.List;
import com.tiger.system.domain.SysVolunteerOrganization;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 志愿组织信息Service接口
 * 
 * @author tiger
 * @date 2024-03-24
 */
public interface ISysVolunteerOrganizationService extends IService<SysVolunteerOrganization> {
    /**
     * 查询志愿组织信息
     * 
     * @param id 志愿组织信息主键
     * @return 志愿组织信息
     */
    SysVolunteerOrganization selectSysVolunteerOrganizationById(Long id);

    /**
     * 查询志愿组织信息列表
     * 
     * @param sysVolunteerOrganization 志愿组织信息
     * @return 志愿组织信息集合
     */
    List<SysVolunteerOrganization> selectSysVolunteerOrganizationList(SysVolunteerOrganization sysVolunteerOrganization);

    /**
     * 新增志愿组织信息
     * 
     * @param sysVolunteerOrganization 志愿组织信息
     * @return 结果
     */
    int insertSysVolunteerOrganization(SysVolunteerOrganization sysVolunteerOrganization);

    /**
     * 修改志愿组织信息
     * 
     * @param sysVolunteerOrganization 志愿组织信息
     * @return 结果
     */
    int updateSysVolunteerOrganization(SysVolunteerOrganization sysVolunteerOrganization);

    /**
     * 批量删除志愿组织信息
     * 
     * @param ids 需要删除的志愿组织信息主键集合
     * @return 结果
     */
    int deleteSysVolunteerOrganizationByIds(Long[] ids);

    /**
     * 删除志愿组织信息信息
     * 
     * @param id 志愿组织信息主键
     * @return 结果
     */
    int deleteSysVolunteerOrganizationById(Long id);
}
