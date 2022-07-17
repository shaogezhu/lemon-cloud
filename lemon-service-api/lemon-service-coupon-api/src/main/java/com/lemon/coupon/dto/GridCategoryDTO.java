package com.lemon.coupon.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GridCategoryDTO
 **/
@Data
public class GridCategoryDTO {

    @Length(min = 1, max = 100)
    private String title;

    @Length(min = 1, max = 100)
    private String name;

    @NotBlank
    @Length(min = 1, max = 255)
    private String img;

    @NotNull
    @Positive
    private Long categoryId;

}
