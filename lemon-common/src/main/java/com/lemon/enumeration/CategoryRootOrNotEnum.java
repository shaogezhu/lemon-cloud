package com.lemon.enumeration;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CategoryRootOrNotEnum
 **/
public enum CategoryRootOrNotEnum {
    /**
     * 父分类
     */
    ROOT(1),
    /**
     * 非父分类
     */
    NOT_ROOT(0);

    private int value;

    CategoryRootOrNotEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
