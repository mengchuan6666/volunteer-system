package com.tiger.system.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiger.common.utils.DateUtils;
import com.tiger.system.domain.SysActivity;
import com.tiger.system.domain.vo.SysActivityDto;
import com.tiger.system.domain.vo.SysActivityRelationDto;
import com.tiger.system.service.ISysActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.system.mapper.SysVolunteerActivityRelationMapper;
import com.tiger.system.domain.SysVolunteerActivityRelation;
import com.tiger.system.service.ISysVolunteerActivityRelationService;
import org.springframework.util.CollectionUtils;

/**
 * 志愿者活动关系Service业务层处理
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Service
public class SysVolunteerActivityRelationServiceImpl extends ServiceImpl<SysVolunteerActivityRelationMapper, SysVolunteerActivityRelation> implements ISysVolunteerActivityRelationService
{
    @Autowired
    private SysVolunteerActivityRelationMapper sysVolunteerActivityRelationMapper;

    @Autowired
    private ISysActivityService sysActivityService;

    /**
     * 查询志愿者活动关系
     * 
     * @param id 志愿者活动关系主键
     * @return 志愿者活动关系
     */
    @Override
    public SysActivityRelationDto selectSysVolunteerActivityRelationById(Long id)
    {
        return sysVolunteerActivityRelationMapper.selectSysVolunteerActivityRelationById(id);
    }

    /**
     * 查询志愿者活动关系列表
     * 
     * @param sysVolunteerActivityRelation 志愿者活动关系
     * @return 志愿者活动关系
     */
    @Override
    public List<SysActivityRelationDto> selectSysVolunteerActivityRelationList(SysVolunteerActivityRelation sysVolunteerActivityRelation)
    {
        return sysVolunteerActivityRelationMapper.selectSysVolunteerActivityRelationList(sysVolunteerActivityRelation);
    }

    /**
     * 新增志愿者活动关系
     * 
     * @param sysVolunteerActivityRelation 志愿者活动关系
     * @return 结果
     */
    @Override
    public int insertSysVolunteerActivityRelation(SysVolunteerActivityRelation sysVolunteerActivityRelation) throws Exception {
        sysVolunteerActivityRelation.setCreateTime(DateUtils.getNowDate());
        SysActivity sysActivity1 = new SysActivity();
        sysActivity1.setId(sysVolunteerActivityRelation.getActivityId());
        List<SysActivityDto> sysActivityList = sysActivityService.selectSysActivityList(sysActivity1);
        if (CollectionUtils.isEmpty(sysActivityList)){
            throw new Exception("活动id不存在");
        }
        if (sysActivityList.get(0).getNumberLimit() <= sysActivityList.get(0).getAttendPeopleCount()){
            log.warn("活动人数已满");
            throw new Exception("活动人数已满");
        }
        // 针对参加活动人数加1
        boolean update = sysActivityService.lambdaUpdate()
                .setSql("attend_people_count = attend_people_count + 1")
                .eq(SysActivity::getId, sysVolunteerActivityRelation.getActivityId())
                .update();
        if (Boolean.TRUE.equals(update)){
            return sysVolunteerActivityRelationMapper.insertSysVolunteerActivityRelation(sysVolunteerActivityRelation);
        }
        return 0;
    }

    /**
     * 修改志愿者活动关系
     * 
     * @param sysVolunteerActivityRelation 志愿者活动关系
     * @return 结果
     */
    @Override
    public int updateSysVolunteerActivityRelation(SysVolunteerActivityRelation sysVolunteerActivityRelation)
    {
        sysVolunteerActivityRelation.setUpdateTime(DateUtils.getNowDate());
        if ("5".equals(sysVolunteerActivityRelation.getStatus()) || "3".equals(sysVolunteerActivityRelation.getStatus())) {
            sysActivityService.lambdaUpdate()
                    .setSql("attend_people_count = attend_people_count - 1")
                    .eq(SysActivity::getId, sysVolunteerActivityRelation.getActivityId())
                    .update();
        }
        return sysVolunteerActivityRelationMapper.updateSysVolunteerActivityRelation(sysVolunteerActivityRelation);
    }

    /**
     * 批量删除志愿者活动关系
     * 
     * @param ids 需要删除的志愿者活动关系主键
     * @return 结果
     */
    @Override
    public int deleteSysVolunteerActivityRelationByIds(Long[] ids)
    {
        LambdaQueryWrapper<SysVolunteerActivityRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysVolunteerActivityRelation::getId, Arrays.asList(ids));
        List<SysVolunteerActivityRelation> activityRelationList = sysVolunteerActivityRelationMapper.selectList(queryWrapper);
        for (SysVolunteerActivityRelation sysVolunteerActivityRelation : activityRelationList) {
            sysActivityService.lambdaUpdate()
                    .setSql("attend_people_count = attend_people_count - 1 ")
                    .eq(SysActivity::getId, sysVolunteerActivityRelation.getActivityId())
                    .update();
        }
        return sysVolunteerActivityRelationMapper.deleteSysVolunteerActivityRelationByIds(ids);
    }

    /**
     * 删除志愿者活动关系信息
     * 
     * @param id 志愿者活动关系主键
     * @return 结果
     */
    @Override
    public int deleteSysVolunteerActivityRelationById(Long id)
    {
        return sysVolunteerActivityRelationMapper.deleteSysVolunteerActivityRelationById(id);
    }
}
