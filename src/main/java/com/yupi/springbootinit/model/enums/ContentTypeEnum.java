package com.yupi.springbootinit.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 帖子内容类型枚举
 *
 * @author pine
 */
public enum ContentTypeEnum {

    COMMON("common", 0),
    MARKDOWN("markdown", 1),
    RICH_TEXT("富文本", 2);

    private final String text;

    private final Integer value;

    ContentTypeEnum(String text, Integer value) {
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
     * @return {@link ContentTypeEnum}
     */
    public static ContentTypeEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ContentTypeEnum contentTypeEnum : ContentTypeEnum.values()) {
            if (contentTypeEnum.value.equals(value)) {
                return contentTypeEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
