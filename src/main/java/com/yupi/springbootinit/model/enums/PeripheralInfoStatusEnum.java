package com.yupi.springbootinit.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品状态枚举
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
public enum PeripheralInfoStatusEnum {

    DISABLED("不可浏览", 0),
    ENABLED("可浏览", 1);

    private final String text;

    private final int value;

    PeripheralInfoStatusEnum(String text, int value) {
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
     * @return {@link PeripheralInfoStatusEnum}
     */
    public static PeripheralInfoStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (PeripheralInfoStatusEnum productStatusEnum : PeripheralInfoStatusEnum.values()) {
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
