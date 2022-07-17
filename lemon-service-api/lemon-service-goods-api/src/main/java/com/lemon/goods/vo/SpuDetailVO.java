package com.lemon.goods.vo;

import lombok.Data;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuDetailVO
 **/
@Data
public class SpuDetailVO {

    private Long id;
    private String title;
    private String subtitle;
    private Long categoryId;
    private Integer rootCategoryId;
    private Integer online;
    private String price;
    private Integer sketchSpecId;
    private Integer defaultSkuId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private Boolean isTest;
    private String forThemeImg;
    private String spuThemeImg;
    private String categoryName;
    private String sketchSpecKeyName;
    private String defaultSkuTitle;
    private List<String> spuImgList;
    private List<String> spuDetailImgList;
}

