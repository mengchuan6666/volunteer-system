package com.tiger.system.service;

import java.util.List;
import com.tiger.system.domain.SysVolunteer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 志愿者信息Service接口
 * 
 * @author tiger
 * @date 2024-03-24
 */
public interface ISysVolunteerService extends IService<SysVolunteer> {
    /**
     * 查询志愿者信息
     * 
     * @param id 志愿者信息主键
     * @return 志愿者信息
     */
    SysVolunteer selectSysVolunteerById(Long id);

    /**
     * 查询志愿者信息列表
     * 
     * @param sysVolunteer 志愿者信息
     * @return 志愿者信息集合
     */
    List<SysVolunteer> selectSysVolunteerList(SysVolunteer sysVolunteer);

    /**
     * 新增志愿者信息
     * 
     * @param sysVolunteer 志愿者信息
     * @return 结果
     */
    int insertSysVolunteer(SysVolunteer sysVolunteer);

    /**
     * 修改志愿者信息
     * 
     * @param sysVolunteer 志愿者信息
     * @return 结果
     */
    int updateSysVolunteer(SysVolunteer sysVolunteer);

    /**
     * 批量删除志愿者信息
     * 
     * @param ids 需要删除的志愿者信息主键集合
     * @return 结果
     */
    int deleteSysVolunteerByIds(Long[] ids);

    /**
     * 删除志愿者信息信息
     * 
     * @param id 志愿者信息主键
     * @return 结果
     */
    int deleteSysVolunteerById(Long id);
}
