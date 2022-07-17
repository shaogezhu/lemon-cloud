package com.lemon.coupon.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerItemDTO
 **/
@Data
public class BannerItemDTO {

    @NotBlank
    @Length(min = 1, max = 255)
    private String img;

    @Length(min = 1, max = 255)
    private String name;

    @NotNull
    @Positive
    private String type;

    @NotNull
    @Positive
    private Long bannerId;

    @Length(min = 1, max = 50)
    private String keyword;

}
