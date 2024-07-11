package com.tiger.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.tiger.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.*;
import com.tiger.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 志愿者活动关系对象 sys_volunteer_activity_relation
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Data
@TableName(value = "sys_volunteer_activity_relation")
@ApiModel(value = "志愿者活动关系")
public class SysVolunteerActivityRelation {
    private static final long serialVersionUID = 1L;

    /** 志愿者活动关系id */
    @ApiModelProperty("志愿者活动关系id")
    private Long id;

    /** 活动id */
    @ApiModelProperty("活动id")
    @NotNull(message = "活动id不能为空")
    @Excel(name = "活动id")
    private Long activityId;

    /** 志愿者id */
    @ApiModelProperty("志愿者id")
    @NotNull(message = "志愿者id不能为空")
    @Excel(name = "志愿者id")
    private Long volunteerId;

    /** 用户id */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    @Excel(name = "用户id")
    private Long userId;


    /** 用户申请活动审批状态 **/
    @ApiModelProperty("用户申请活动审批状态")
    @Excel(name = "用户申请活动审批状态")
    private String status;

    /** 扩展字段 */
    @ApiModelProperty("扩展字段")
    @Excel(name = "扩展字段")
    private String extra;

    private Date createTime;
    private Date updateTime;
    @JsonIgnore
    @TableField(exist = false)
    private Integer organizationId;

    @JsonIgnore
    @TableField(exist = false)
    private String activityName;
    @JsonIgnore
    @TableField(exist = false)
    private Integer activityType;
    @JsonIgnore
    @TableField(exist = false)
    private String organizer;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activityId", getActivityId())
            .append("volunteerId", getVolunteerId())
            .append("userId", getUserId())
            .append("extra", getExtra())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
