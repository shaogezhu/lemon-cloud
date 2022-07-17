package com.lemon.coupon.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName GridCategory
 * @author shaogezhu
 * @version 1.0.0
**/
@Getter
@Setter
@TableName("grid_category")
public class GridCategory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143445L;
    @TableId
    private Long id;
    private String title;
    private String img;
    private String name;
    @TableField("category_id")
    private Long categoryId;
    @TableField("root_category_id")
    private Long rootCategoryId;
}

