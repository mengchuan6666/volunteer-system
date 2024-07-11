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
 * 评论信息对象 sys_comment
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Data
@TableName(value = "sys_comment")
@ApiModel(value = "评论信息")
public class SysComment extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 评论id */
    @ApiModelProperty("评论id")
    private Long id;

    /** 用户id */
    @ApiModelProperty("用户id")
    @Excel(name = "用户id")
    private Long userId;

    /** 活动id */
    @ApiModelProperty("活动id")
    @Excel(name = "活动id")
    private Long activittyId;

    /** 评论内容 */
    @ApiModelProperty("评论内容")
    @Excel(name = "评论内容")
    private String commentContent;

    /** 扩展字段 */
    @ApiModelProperty("扩展字段")
    @Excel(name = "扩展字段")
    private String extra;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("activittyId", getActivittyId())
            .append("commentContent", getCommentContent())
            .append("extra", getExtra())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
