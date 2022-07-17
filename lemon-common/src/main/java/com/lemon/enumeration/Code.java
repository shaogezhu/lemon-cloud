package com.lemon.enumeration;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Code
 **/
public enum Code {

    SUCCESS(0, "OK", "成功"),

    CREATED(1, "Created", "创建成功"),

    UPDATED(2, "Updated", "更新成功"),

    DELETED(3, "Deleted", "删除成功");

    private int code;

    private String description;

    private String zhDescription;

    Code(int code, String description, String zhDescription) {
        this.code = code;
        this.description = description;
        this.zhDescription = zhDescription;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getZhDescription() {
        return zhDescription;
    }
}