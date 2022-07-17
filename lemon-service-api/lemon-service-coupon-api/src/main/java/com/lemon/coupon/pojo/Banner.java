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
 * @ClassName Banner
 **/
@Setter
@Getter
@TableName("banner")
public class Banner extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143442L;
    @TableId
    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;

    @TableField(exist = false)
    private List<BannerItem> items;
}
