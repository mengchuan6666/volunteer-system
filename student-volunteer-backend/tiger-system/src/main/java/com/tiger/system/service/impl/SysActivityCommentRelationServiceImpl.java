package com.tiger.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiger.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.system.mapper.SysActivityCommentRelationMapper;
import com.tiger.system.domain.SysActivityCommentRelation;
import com.tiger.system.service.ISysActivityCommentRelationService;
/**
 * 活动评论关系Service业务层处理
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Service
public class SysActivityCommentRelationServiceImpl extends ServiceImpl<SysActivityCommentRelationMapper, SysActivityCommentRelation> implements ISysActivityCommentRelationService
{
    @Autowired
    private SysActivityCommentRelationMapper sysActivityCommentRelationMapper;

    /**
     * 查询活动评论关系
     * 
     * @param id 活动评论关系主键
     * @return 活动评论关系
     */
    @Override
    public SysActivityCommentRelation selectSysActivityCommentRelationById(Long id)
    {
        return sysActivityCommentRelationMapper.selectSysActivityCommentRelationById(id);
    }

    /**
     * 查询活动评论关系列表
     * 
     * @param sysActivityCommentRelation 活动评论关系
     * @return 活动评论关系
     */
    @Override
    public List<SysActivityCommentRelation> selectSysActivityCommentRelationList(SysActivityCommentRelation sysActivityCommentRelation)
    {
        return sysActivityCommentRelationMapper.selectSysActivityCommentRelationList(sysActivityCommentRelation);
    }

    /**
     * 新增活动评论关系
     * 
     * @param sysActivityCommentRelation 活动评论关系
     * @return 结果
     */
    @Override
    public int insertSysActivityCommentRelation(SysActivityCommentRelation sysActivityCommentRelation)
    {
        sysActivityCommentRelation.setCreateTime(DateUtils.getNowDate());
        return sysActivityCommentRelationMapper.insertSysActivityCommentRelation(sysActivityCommentRelation);
    }

    /**
     * 修改活动评论关系
     * 
     * @param sysActivityCommentRelation 活动评论关系
     * @return 结果
     */
    @Override
    public int updateSysActivityCommentRelation(SysActivityCommentRelation sysActivityCommentRelation)
    {
        sysActivityCommentRelation.setUpdateTime(DateUtils.getNowDate());
        return sysActivityCommentRelationMapper.updateSysActivityCommentRelation(sysActivityCommentRelation);
    }

    /**
     * 批量删除活动评论关系
     * 
     * @param ids 需要删除的活动评论关系主键
     * @return 结果
     */
    @Override
    public int deleteSysActivityCommentRelationByIds(Long[] ids)
    {
        return sysActivityCommentRelationMapper.deleteSysActivityCommentRelationByIds(ids);
    }

    /**
     * 删除活动评论关系信息
     * 
     * @param id 活动评论关系主键
     * @return 结果
     */
    @Override
    public int deleteSysActivityCommentRelationById(Long id)
    {
        return sysActivityCommentRelationMapper.deleteSysActivityCommentRelationById(id);
    }
}
