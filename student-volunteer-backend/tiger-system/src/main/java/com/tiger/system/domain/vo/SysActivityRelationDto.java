package com.tiger.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiger.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @programName: com.tiger.system.domain.vo
 * @ClassName:SysActivityRelationDto
 * @Description TODO
 * @Author: wwding
 * @Create: 2024/3/27 14:41
 **/
@Data
public class SysActivityRelationDto implements Serializable {
    private static final long serialVersionUID = 4867470748216132365L;

    /** 活动ID */
    @ApiModelProperty("活动ID")
    private Long activityId;

    /** 活动名称 */
    @ApiModelProperty("活动名称")
    @NotBlank(message = "活动名称不能为空")
    @Excel(name = "活动名称")
    private String activityName;

    /** 活动描述 */
    @ApiModelProperty("活动描述")
    @NotBlank(message = "活动描述不能为空")
    @Excel(name = "活动描述")
    private String describe;

    /** 活动地点 */
    @ApiModelProperty("活动地点")
    @NotBlank(message = "活动地点不能为空")
    @Excel(name = "活动地点")
    private String location;

    /** 开始时间 */
    @ApiModelProperty("开始时间")
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @ApiModelProperty("结束时间")
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 主办方 */
    @ApiModelProperty("主办方")
    // @NotBlank(message = "主办方不能为空")
    @Excel(name = "主办方")
    private String organizer;

    /** 活动状态 */
    @ApiModelProperty("活动状态")
    // @NotBlank(message = "活动状态不能为空")
    @Excel(name = "活动状态")
    private String activityStatus;

    /** 联系人 */
    @ApiModelProperty("联系人")
    @NotBlank(message = "联系人不能为空")
    @Excel(name = "联系人")
    private String contacts;

    /** 联系人电话 */
    @ApiModelProperty("联系人电话")
    @NotBlank(message = "联系人电话不能为空")
    @Excel(name = "联系人电话")
    private String contactsPhone;

    /** 活动所需物资 */
    @ApiModelProperty("活动所需物资")
    @Excel(name = "活动所需物资")
    private String substance;

    /** 志愿者要求 */
    @ApiModelProperty("志愿者要求")
    @NotBlank(message = "志愿者要求不能为空")
    @Excel(name = "志愿者要求")
    private String ask;

    /** 活动图片 */
    @ApiModelProperty("活动图片")
    @Excel(name = "活动图片")
    private String imgs;

    /** 活动类型 */
    @ApiModelProperty("活动类型")
    @NotBlank(message = "活动类型不能为空")
    @Excel(name = "活动类型")
    private String activityType;

    /** 志愿组织id */
    @ApiModelProperty("志愿组织id")
    @NotNull(message = "志愿组织id不能为空")
    @Excel(name = "志愿组织id")
    private Long organizationId;

    /** 扩展字段 */
    @ApiModelProperty("扩展字段")
    @Excel(name = "扩展字段")
    private String extra;

    /** 人数限制 **/
    @ApiModelProperty("活动人数限制")
    @Excel(name = "活动人数限制")
    @NotNull(message = "活动人数限制不能为空")
    private Integer numberLimit;

    /** 参加活动人数 **/
    @Excel(name = "活动报名人数")
    @ApiModelProperty("活动报名人数")
    private Integer attendPeopleCount;

    @JsonIgnore
    private Long userId;

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Long volunteerId;

    /** 志愿者名称 */
    @ApiModelProperty("志愿者名称")
    @NotBlank(message = "志愿者名称不能为空")
    @Excel(name = "志愿者名称")
    private String volunteerName;

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
    private String volunteerStatus;


    /** 加入志愿者时间 */
    @ApiModelProperty("加入志愿者时间")
    // @NotNull(message = "加入志愿者时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "加入志愿者时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerTime;

    /** 志愿者活动关系id */
    @ApiModelProperty("志愿者活动关系id")
    private Long relationId;

    /** 用户申请活动审批状态 **/
    @ApiModelProperty("用户申请活动审批状态")
    @Excel(name = "用户申请活动审批状态")
    private String userApprovalStatus;
}
