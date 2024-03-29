package com.yupi.springbootinit.model.dto.applyrecords;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName report
 */
@Data
public class ApplyRecordsApplyRequest implements Serializable {


    /**
     * 关联到周边信息表中的 id 字段，表示申请的是哪个周边
     */
    private Long peripheralId;


    /**
     * 申请内容（xxx申请xxx周边一件）
     */
    private String content;

    /**
     * 申请数量
     */
    private Integer applyNums;


    private static final long serialVersionUID = 1L;
}