package com.lemon.coupon.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerItem
 **/
@Getter
@Setter
@TableName("banner_item")
public class BannerItem extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143444L;
    @TableId
    private Long id;
    private String img;
    private String keyword;
    private Integer type;
    @TableField("banner_id")
    private Long bannerId;
    private String name;
}
