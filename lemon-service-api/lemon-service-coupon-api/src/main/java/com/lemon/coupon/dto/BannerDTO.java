package com.lemon.coupon.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerDTO
 **/
@Getter
@Setter
public class BannerDTO {

    @NotBlank
    @Length(min = 2, max = 20)
    private String name;

    @Length(min = 2, max = 30)
    private String title;

    @Length(min = 2, max = 256)
    private String img;

    @Length(min = 2, max = 256)
    private String description;

}