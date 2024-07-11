package com.tiger.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiger.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.system.mapper.SysCommentMapper;
import com.tiger.system.domain.SysComment;
import com.tiger.system.service.ISysCommentService;
/**
 * 评论信息Service业务层处理
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Service
public class SysCommentServiceImpl extends ServiceImpl<SysCommentMapper, SysComment> implements ISysCommentService
{
    @Autowired
    private SysCommentMapper sysCommentMapper;

    /**
     * 查询评论信息
     * 
     * @param id 评论信息主键
     * @return 评论信息
     */
    @Override
    public SysComment selectSysCommentById(Long id)
    {
        return sysCommentMapper.selectSysCommentById(id);
    }

    /**
     * 查询评论信息列表
     * 
     * @param sysComment 评论信息
     * @return 评论信息
     */
    @Override
    public List<SysComment> selectSysCommentList(SysComment sysComment)
    {
        return sysCommentMapper.selectSysCommentList(sysComment);
    }

    /**
     * 新增评论信息
     * 
     * @param sysComment 评论信息
     * @return 结果
     */
    @Override
    public int insertSysComment(SysComment sysComment)
    {
        sysComment.setCreateTime(DateUtils.getNowDate());
        return sysCommentMapper.insertSysComment(sysComment);
    }

    /**
     * 修改评论信息
     * 
     * @param sysComment 评论信息
     * @return 结果
     */
    @Override
    public int updateSysComment(SysComment sysComment)
    {
        sysComment.setUpdateTime(DateUtils.getNowDate());
        return sysCommentMapper.updateSysComment(sysComment);
    }

    /**
     * 批量删除评论信息
     * 
     * @param ids 需要删除的评论信息主键
     * @return 结果
     */
    @Override
    public int deleteSysCommentByIds(Long[] ids)
    {
        return sysCommentMapper.deleteSysCommentByIds(ids);
    }

    /**
     * 删除评论信息信息
     * 
     * @param id 评论信息主键
     * @return 结果
     */
    @Override
    public int deleteSysCommentById(Long id)
    {
        return sysCommentMapper.deleteSysCommentById(id);
    }
}
