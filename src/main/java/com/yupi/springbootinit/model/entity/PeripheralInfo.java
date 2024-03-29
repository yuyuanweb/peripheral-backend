package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 周边
 * @TableName peripheral_info
 */
@TableName(value ="peripheral_info")
@Data
public class PeripheralInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片
     */
    private String cover;

    /**
     * 价格（分）
     */
    private Integer price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 周边类型
     */
    private String type;

    /**
     * 进货链接
     */
    private String replenishLink;

    /**
     * 用户购买链接
     */
    private String purchaseLink;

    /**
     * 是否公开浏览，0: 关闭，1: 启用，默认关闭
     */
    private Integer status;

    /**
     * 存储所有权限的 JSON 结构
     */
    private String permission;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}