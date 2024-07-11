package com.tiger.system.mapper;

import java.util.List;
import com.tiger.system.domain.SysActivityCommentRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 活动评论关系Mapper接口
 * 
 * @author tiger
 * @date 2024-03-24
 */
public interface SysActivityCommentRelationMapper extends BaseMapper<SysActivityCommentRelation> {
    /**
     * 查询活动评论关系
     *
     * @param id 活动评论关系主键
     * @return 活动评论关系
     */
    SysActivityCommentRelation selectSysActivityCommentRelationById(Long id);

    /**
     * 查询活动评论关系列表
     *
     * @param sysActivityCommentRelation 活动评论关系
     * @return 活动评论关系集合
     */
    List<SysActivityCommentRelation> selectSysActivityCommentRelationList(SysActivityCommentRelation sysActivityCommentRelation);

    /**
     * 新增活动评论关系
     *
     * @param sysActivityCommentRelation 活动评论关系
     * @return 结果
     */
    int insertSysActivityCommentRelation(SysActivityCommentRelation sysActivityCommentRelation);

    /**
     * 修改活动评论关系
     *
     * @param sysActivityCommentRelation 活动评论关系
     * @return 结果
     */
    int updateSysActivityCommentRelation(SysActivityCommentRelation sysActivityCommentRelation);

    /**
     * 删除活动评论关系
     *
     * @param id 活动评论关系主键
     * @return 结果
     */
    int deleteSysActivityCommentRelationById(Long id);

    /**
     * 批量删除活动评论关系
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSysActivityCommentRelationByIds(Long[] ids);
}
