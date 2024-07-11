package com.tiger.system.service;

import java.util.List;
import com.tiger.system.domain.SysVolunteerActivityRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiger.system.domain.vo.SysActivityRelationDto;

/**
 * 志愿者活动关系Service接口
 * 
 * @author tiger
 * @date 2024-03-24
 */
public interface ISysVolunteerActivityRelationService extends IService<SysVolunteerActivityRelation> {
    /**
     * 查询志愿者活动关系
     * 
     * @param id 志愿者活动关系主键
     * @return 志愿者活动关系
     */
    SysActivityRelationDto selectSysVolunteerActivityRelationById(Long id);

    /**
     * 查询志愿者活动关系列表
     * 
     * @param sysVolunteerActivityRelation 志愿者活动关系
     * @return 志愿者活动关系集合
     */
    List<SysActivityRelationDto> selectSysVolunteerActivityRelationList(SysVolunteerActivityRelation sysVolunteerActivityRelation);

    /**
     * 新增志愿者活动关系
     * 
     * @param sysVolunteerActivityRelation 志愿者活动关系
     * @return 结果
     */
    int insertSysVolunteerActivityRelation(SysVolunteerActivityRelation sysVolunteerActivityRelation) throws Exception;

    /**
     * 修改志愿者活动关系
     * 
     * @param sysVolunteerActivityRelation 志愿者活动关系
     * @return 结果
     */
    int updateSysVolunteerActivityRelation(SysVolunteerActivityRelation sysVolunteerActivityRelation);

    /**
     * 批量删除志愿者活动关系
     * 
     * @param ids 需要删除的志愿者活动关系主键集合
     * @return 结果
     */
    int deleteSysVolunteerActivityRelationByIds(Long[] ids);

    /**
     * 删除志愿者活动关系信息
     * 
     * @param id 志愿者活动关系主键
     * @return 结果
     */
    int deleteSysVolunteerActivityRelationById(Long id);
}
