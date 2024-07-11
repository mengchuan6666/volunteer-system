package com.tiger.system.service;

import java.util.List;
import com.tiger.system.domain.SysMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 留言板信息Service接口
 * 
 * @author tiger
 * @date 2024-03-24
 */
public interface ISysMessageService extends IService<SysMessage> {
    /**
     * 查询留言板信息
     * 
     * @param id 留言板信息主键
     * @return 留言板信息
     */
    SysMessage selectSysMessageById(Long id);

    /**
     * 查询留言板信息列表
     * 
     * @param sysMessage 留言板信息
     * @return 留言板信息集合
     */
    List<SysMessage> selectSysMessageList(SysMessage sysMessage);

    /**
     * 新增留言板信息
     * 
     * @param sysMessage 留言板信息
     * @return 结果
     */
    int insertSysMessage(SysMessage sysMessage);

    /**
     * 修改留言板信息
     * 
     * @param sysMessage 留言板信息
     * @return 结果
     */
    int updateSysMessage(SysMessage sysMessage);

    /**
     * 批量删除留言板信息
     * 
     * @param ids 需要删除的留言板信息主键集合
     * @return 结果
     */
    int deleteSysMessageByIds(Long[] ids);

    /**
     * 删除留言板信息信息
     * 
     * @param id 留言板信息主键
     * @return 结果
     */
    int deleteSysMessageById(Long id);
}
