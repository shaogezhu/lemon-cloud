package com.lemon.goods.pojo;

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
 * @ClassName Spu
 **/
@Setter
@Getter
@TableName("spu")
public class Spu extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143658L;
    @TableId
    private Long id;
    private String title;
    private String subtitle;
    @TableField("category_id")
    private Long categoryId;
    @TableField("root_category_id")
    private Long rootCategoryId;
    private Integer online;
    private String price;
    @TableField("sketch_spec_id")
    private Long sketchSpecId;
    @TableField("default_sku_id")
    private Long defaultSkuId;
    private String img;
    @TableField("discount_price")
    private String discountPrice;
    private String description;
    private String tags;
    @TableField("is_test")
    private Boolean isTest;
    @TableField("for_theme_img")
    private String forThemeImg;
    @TableField("spu_theme_img")
    private String spuThemeImg;


    @TableField(exist = false)
    private List<Sku> skuList;

    @TableField(exist = false)
    private List<SpuImg> spuImgList;

    @TableField(exist = false)
    private List<SpuDetailImg> spuDetailImgList;

}