package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 申请记录表
 * @TableName apply_records
 */
@TableName(value ="apply_records")
@Data
public class ApplyRecords implements Serializable {
    /**
     * 主键，用于唯一标识每条申请记录
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联到周边信息表中的 id 字段，表示申请的是哪个周边
     */
    private Long peripheralId;

    /**
     * 周边名称
     */
    private String peripheralName;

    /**
     * 关联到用户表中的 id 字段，表示申请者的用户ID
     */
    private Long applicantId;

    /**
     * 审核人id
     */
    private Long adminId;

    /**
     * 关联到用户表中的 userName 字段，表示申请者的用户名
     */
    private String applicantUserName;

    /**
     * 记录申请的时间
     */
    private Date applicationTime;

    /**
     * 申请状态（0：未申请，1：审核中，2：通过审核，3：审核不通过）
     */
    private Integer status;

    /**
     * 申请内容（xxx申请xxx周边一件）
     */
    private String content;

    /**
     * 审核通过或者不通过的理由
     */
    private String reason;

    /**
     * 申请数量
     */
    private Integer applyNums;

    /**
     * 是否删除，0: 未删除，1: 已删除，默认未删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录最后更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}