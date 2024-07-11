package com.tiger.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.tiger.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.*;
import com.tiger.common.core.domain.BaseEntity;

/**
 * 留言板信息对象 sys_message
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Data
@TableName(value = "sys_message")
@ApiModel(value = "留言板信息")
public class SysMessage extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 留言id */
    @ApiModelProperty("留言id")
    private Long id;

    /** 用户id */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    @Excel(name = "用户id")
    private Long userId;

    /** 留言内容 */
    @ApiModelProperty("留言内容")
    @NotBlank(message = "留言内容不能为空")
    @Excel(name = "留言内容")
    private String conent;

    /** 留言处理状态 */
    @ApiModelProperty("留言处理状态")
    // @NotBlank(message = "留言处理状态不能为空")
    @Excel(name = "留言处理状态")
    private String status;

    /** 留言回复 */
    @ApiModelProperty("留言回复")
    // @NotBlank(message = "留言回复不能为空")
    @Excel(name = "留言回复")
    private String answer;

    /** 扩展字段 */
    @ApiModelProperty("扩展字段")
    @Excel(name = "扩展字段")
    private String extra;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("conent", getConent())
            .append("status", getStatus())
            .append("answer", getAnswer())
            .append("extra", getExtra())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
