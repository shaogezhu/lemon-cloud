package com.lemon.goods.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuSimplifyVO
 **/
@Getter
@Setter
public class SpuSimplifyVO {
    private Long id;
    private String title;
    private String subtitle;
    private String img;
    private String forThemeImg;
    private String price;
    private String discountPrice;
    private String description;
    private String tags;
    private String sketchSpecId;
    private Integer tid;
}

