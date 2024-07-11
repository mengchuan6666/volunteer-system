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
 * 活动评论关系对象 sys_activity_comment_relation
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Data
@TableName(value = "sys_activity_comment_relation")
@ApiModel(value = "活动评论关系")
public class SysActivityCommentRelation extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 活动评论关系id */
    @ApiModelProperty("活动评论关系id")
    private Long id;

    /** 活动id */
    @ApiModelProperty("活动id")
    @NotNull(message = "活动id不能为空")
    @Excel(name = "活动id")
    private Long activityId;

    /** 评论id */
    @ApiModelProperty("评论id")
    @NotNull(message = "评论id不能为空")
    @Excel(name = "评论id")
    private Long commentId;

    /** 用户id */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    @Excel(name = "用户id")
    private Long userId;

    /** 志愿者id */
    @ApiModelProperty("志愿者id")
    @Excel(name = "志愿者id")
    private Long volunteerId;

    /** 扩展字段 */
    @ApiModelProperty("扩展字段")
    @Excel(name = "扩展字段")
    private String extra;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activityId", getActivityId())
            .append("commentId", getCommentId())
            .append("userId", getUserId())
            .append("volunteerId", getVolunteerId())
            .append("extra", getExtra())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
