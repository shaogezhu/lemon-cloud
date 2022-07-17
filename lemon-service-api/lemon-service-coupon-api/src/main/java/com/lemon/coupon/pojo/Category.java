package com.lemon.coupon.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Category
 **/
@Getter
@Setter
@TableName("category")
public class Category extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143445L;
    @TableId
    private Long id;

    private String name;

    private String description;

    @TableField("is_root")
    private Integer isRoot;

    private String img;

    @TableField("parent_id")
    private Long parentId;

    @TableField("`index`")
    private Integer index;

    @TableField("online")
    private Integer online;

    private Integer level;

    @TableField(exist = false)
    private List<Coupon> couponList;

}
