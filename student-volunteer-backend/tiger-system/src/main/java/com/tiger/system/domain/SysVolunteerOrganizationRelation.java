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
 * 志愿者志愿组织关系对象 sys_volunteer_organization_relation
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Data
@TableName(value = "sys_volunteer_organization_relation")
@ApiModel(value = "志愿者志愿组织关系")
public class SysVolunteerOrganizationRelation extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 志愿者与志愿组织关系id */
    @ApiModelProperty("志愿者与志愿组织关系id")
    private Long id;

    /** 志愿组织id */
    @ApiModelProperty("志愿组织id")
    @NotNull(message = "志愿组织id不能为空")
    @Excel(name = "志愿组织id")
    private Long organizationId;

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

    /** 志愿者与志愿组织审核状态 */
    @ApiModelProperty("志愿者与志愿组织审核状态")
    @Excel(name = "志愿者与志愿组织审核状态")
    private String status;

    /** 扩展字段 */
    @ApiModelProperty("扩展字段")
    @Excel(name = "扩展字段")
    private String extra;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("organizationId", getOrganizationId())
            .append("volunteerId", getVolunteerId())
            .append("userId", getUserId())
            .append("status", getStatus())
            .append("extra", getExtra())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
