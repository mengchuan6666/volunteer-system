package com.tiger.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiger.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tiger.system.mapper.SysMessageMapper;
import com.tiger.system.domain.SysMessage;
import com.tiger.system.service.ISysMessageService;
/**
 * 留言板信息Service业务层处理
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService
{
    @Autowired
    private SysMessageMapper sysMessageMapper;

    /**
     * 查询留言板信息
     * 
     * @param id 留言板信息主键
     * @return 留言板信息
     */
    @Override
    public SysMessage selectSysMessageById(Long id)
    {
        return sysMessageMapper.selectSysMessageById(id);
    }

    /**
     * 查询留言板信息列表
     * 
     * @param sysMessage 留言板信息
     * @return 留言板信息
     */
    @Override
    public List<SysMessage> selectSysMessageList(SysMessage sysMessage)
    {
        return sysMessageMapper.selectSysMessageList(sysMessage);
    }

    /**
     * 新增留言板信息
     * 
     * @param sysMessage 留言板信息
     * @return 结果
     */
    @Override
    public int insertSysMessage(SysMessage sysMessage)
    {
        sysMessage.setCreateTime(DateUtils.getNowDate());
        return sysMessageMapper.insertSysMessage(sysMessage);
    }

    /**
     * 修改留言板信息
     * 
     * @param sysMessage 留言板信息
     * @return 结果
     */
    @Override
    public int updateSysMessage(SysMessage sysMessage)
    {
        sysMessage.setUpdateTime(DateUtils.getNowDate());
        return sysMessageMapper.updateSysMessage(sysMessage);
    }

    /**
     * 批量删除留言板信息
     * 
     * @param ids 需要删除的留言板信息主键
     * @return 结果
     */
    @Override
    public int deleteSysMessageByIds(Long[] ids)
    {
        return sysMessageMapper.deleteSysMessageByIds(ids);
    }

    /**
     * 删除留言板信息信息
     * 
     * @param id 留言板信息主键
     * @return 结果
     */
    @Override
    public int deleteSysMessageById(Long id)
    {
        return sysMessageMapper.deleteSysMessageById(id);
    }
}
