package com.yupi.springbootinit.model.vo.peripheralInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品视图（脱敏）
 * @author daiyifei
 */
@Data
public class PeripheralInfoVO implements Serializable {
    /**
     * 主键
     */
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
     * 用户购买链接
     */
    private String purchaseLink;


    private static final long serialVersionUID = 1L;
}