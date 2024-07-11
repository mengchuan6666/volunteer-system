package com.tiger.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 志愿者信息对象 sys_volunteer
 * 
 * @author tiger
 * @date 2024-03-24
 */
@Data
@TableName(value = "sys_volunteer")
@ApiModel(value = "志愿者信息")
public class SysVolunteer extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Long id;

    /** 用户id */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    @Excel(name = "用户id")
    private Long userId;

    /** 志愿者名称 */
    @ApiModelProperty("志愿者名称")
    @NotBlank(message = "志愿者名称不能为空")
    @Excel(name = "志愿者名称")
    private String name;

    /** 志愿者性别 */
    @ApiModelProperty("志愿者性别")
    @Excel(name = "志愿者性别")
    private String sex;

    /** 联系方式 */
    @ApiModelProperty("联系方式")
    @Excel(name = "联系方式")
    private String contactPhone;

    /** 志愿者审核状态 */
    @ApiModelProperty("志愿者审核状态")
    @Excel(name = "志愿者审核状态")
    private String status;

    /** 扩展字段 */
    @ApiModelProperty("扩展字段")
    @Excel(name = "扩展字段")
    private String extra;

    /** 加入志愿者时间 */
    @ApiModelProperty("加入志愿者时间")
    // @NotNull(message = "加入志愿者时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "加入志愿者时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerTime;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("sex", getSex())
            .append("contactPhone", getContactPhone())
            .append("extra", getExtra())
            .append("registerTime", getRegisterTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
