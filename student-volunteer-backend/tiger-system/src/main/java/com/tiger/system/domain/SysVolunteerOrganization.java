package com.tiger.system.domain;

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

/**
 * 志愿组织信息对象 sys_volunteer_organization
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Data
@TableName(value = "sys_volunteer_organization")
@ApiModel(value = "志愿组织信息")
public class SysVolunteerOrganization extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 组织id */
    @ApiModelProperty("组织id")
    private Long id;

    /** 组织名 */
    @ApiModelProperty("组织名")
    @NotBlank(message = "组织名不能为空")
    @Excel(name = "组织名")
    private String name;

    /** 组织介绍 */
    @ApiModelProperty("组织介绍")
    @NotBlank(message = "组织介绍不能为空")
    @Excel(name = "组织介绍")
    private String introduce;

    /** 扩展字段 */
    @ApiModelProperty("扩展字段")
    @Excel(name = "扩展字段")
    private String extra;

    @JsonIgnore
    private Long userId;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("introduce", getIntroduce())
            .append("extra", getExtra())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
