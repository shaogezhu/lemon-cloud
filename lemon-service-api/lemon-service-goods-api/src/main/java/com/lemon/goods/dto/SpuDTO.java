package com.lemon.goods.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuDTO
 **/
@Data
public class SpuDTO {

    @NotBlank
    @Length(min = 1, max = 128)
    private String title;

    @Length(min = 1, max = 255)
    private String subtitle;

    @Length(min = 1, max = 255)
    private String img;

    @Length(min = 1, max = 255)
    private String forThemeImg;

    @Positive
    @NotNull
    private Long categoryId;

    @Max(1)
    @Min(0)
    private Integer online;

    @Positive
    private Long sketchSpecId;

    @Positive
    private Long defaultSkuId;

    @NotBlank
    @Length(min = 1, max = 20)
    private String price;

    @Length(min = 1, max = 20)
    private String discountPrice;

    @Length(min = 1, max = 255)
    private String description;

    private String tags;

    private List<Long> specKeyIdList;

    private List<String> spuImgList;

    private List<String> spuDetailImgList;

}
