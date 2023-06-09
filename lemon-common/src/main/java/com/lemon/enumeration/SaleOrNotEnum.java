package com.lemon.enumeration;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SaleOrNotEnum
 **/
public enum SaleOrNotEnum {

    /**
     * 在售
     */
    SALE(1),
    /**
     * 停售
     */
    NOT_SALE(0);

    private int value;

    SaleOrNotEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
