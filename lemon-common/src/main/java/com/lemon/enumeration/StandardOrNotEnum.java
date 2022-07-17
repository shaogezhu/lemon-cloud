package com.lemon.enumeration;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName StandardOrNotEnum
 **/
public enum StandardOrNotEnum {
    /**
     * 标准
     */
    STANDARD(1),
    /**
     * 非标准
     */
    NOT_STANDARD(0);

    private int value;

    StandardOrNotEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
