package com.lemon.coupon.dto;

import com.lemon.enumeration.CategoryRootOrNotEnum;
import com.lemon.enumeration.OnlineOrNotEnum;
import com.lemon.oauth.validator.EnumValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CategoryDTO
 **/
@Data
public class CategoryDTO {
    @NotBlank
    @Length(min = 1, max = 100)
    private String name;

    @Length(min = 1, max = 255)
    private String description;

    @EnumValue(target = CategoryRootOrNotEnum.class)
    private Integer isRoot;

    @Positive
    private Long parentId;

    @Length(min = 1, max = 255)
    private String img;

    @Positive
    private Integer index;

    @EnumValue(target = OnlineOrNotEnum.class)
    private Integer online;

    @Positive
    private Integer level;

}