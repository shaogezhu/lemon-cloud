package com.lemon.goods.vo;

import lombok.Data;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpusimpleVO
 **/
@Data
public class SpuSimpleVO {
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
}

