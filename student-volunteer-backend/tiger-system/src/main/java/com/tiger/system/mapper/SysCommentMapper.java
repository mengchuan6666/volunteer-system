package com.tiger.system.mapper;

import java.util.List;
import com.tiger.system.domain.SysComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 评论信息Mapper接口
 * 
 * @author tiger
 * @date 2024-03-24
 */
public interface SysCommentMapper extends BaseMapper<SysComment> {
    /**
     * 查询评论信息
     *
     * @param id 评论信息主键
     * @return 评论信息
     */
    SysComment selectSysCommentById(Long id);

    /**
     * 查询评论信息列表
     *
     * @param sysComment 评论信息
     * @return 评论信息集合
     */
    List<SysComment> selectSysCommentList(SysComment sysComment);

    /**
     * 新增评论信息
     *
     * @param sysComment 评论信息
     * @return 结果
     */
    int insertSysComment(SysComment sysComment);

    /**
     * 修改评论信息
     *
     * @param sysComment 评论信息
     * @return 结果
     */
    int updateSysComment(SysComment sysComment);

    /**
     * 删除评论信息
     *
     * @param id 评论信息主键
     * @return 结果
     */
    int deleteSysCommentById(Long id);

    /**
     * 批量删除评论信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSysCommentByIds(Long[] ids);
}
