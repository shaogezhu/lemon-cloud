package com.lemon.goods.dto;

import com.lemon.enumeration.StandardOrNotEnum;
import com.lemon.oauth.validator.EnumValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecKeyDTO
 **/
@Data
public class SpecKeyDTO {

    @NotBlank
    @Length(min = 1, max = 100)
    private String name;

    @Length(min = 1, max = 255)
    private String description;

    @Length(min = 1, max = 30)
    private String unit;

    @EnumValue(target = StandardOrNotEnum.class)
    private Integer standard;

}