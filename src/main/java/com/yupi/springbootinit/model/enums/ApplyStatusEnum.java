package com.yupi.springbootinit.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 周边申请状态枚举
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
public enum ApplyStatusEnum {


    NOT_APPLIED( "未申请",0),
    PENDING("审核中",1),
    APPROVED("通过审核",2),
    REJECTED( "审核不通过",3);


    private final String text;

    private final int value;

    ApplyStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return {@link List}<{@link Integer}>
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 值
     * @return {@link ApplyStatusEnum}
     */
    public static ApplyStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ApplyStatusEnum productStatusEnum : ApplyStatusEnum.values()) {
            if (productStatusEnum.getValue() == value) {
                return productStatusEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
