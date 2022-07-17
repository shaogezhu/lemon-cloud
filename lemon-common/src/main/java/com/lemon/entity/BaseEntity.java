package com.lemon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BaseEntity
 **/
@Setter
@Getter
public abstract class BaseEntity {
    @JsonIgnore
    @TableField("create_time")
    private Date createTime;
    @JsonIgnore
    @TableField("update_time")
    private Date updateTime;
    @JsonIgnore
    @TableField("delete_time")
    private Date deleteTime;
}
