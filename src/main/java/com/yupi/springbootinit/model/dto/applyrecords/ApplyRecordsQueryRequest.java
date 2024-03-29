package com.yupi.springbootinit.model.dto.applyrecords;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author https://github.com/liyupi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplyRecordsQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     */
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


    private static final long serialVersionUID = 1L;
}