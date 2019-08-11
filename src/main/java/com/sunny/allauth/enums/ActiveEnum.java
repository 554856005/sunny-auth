package com.sunny.allauth.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * @author JasonLi
 * @date 2019/3/1 19:35
 * @since 1.0
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActiveEnum {
    /**
     * 状态
     */
    NONE(0, "空"), ENABLED(10, "启用"), DISABLE(99, "禁用");
    @Getter
    private int key;
    @Getter
    private String value;

    ActiveEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @JsonCreator
    public static ActiveEnum fromKey(int key) {
        for (ActiveEnum s : ActiveEnum.values()) {
            if (s.key == key) {
                return s;
            }
        }
        return NONE;
    }
}
