package com.yupi.springbootinit.model.dto.peripheralInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 * @TableName report
 */
@Data
public class PeripheralInfoAddRequest implements Serializable {

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
//    private List<String> typeList;

    /**
     * 周边类型
     */
    private List<String> type;

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

    private static final long serialVersionUID = 1L;
}