package com.lemon.enumeration;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LoginType
 **/
public enum LoginType {
    USER_WX(0, "微信登录"),
    USER_Email(1, "邮箱登录");

    private Integer value;

    LoginType(Integer value, String description) {
        this.value = value;
    }

}
