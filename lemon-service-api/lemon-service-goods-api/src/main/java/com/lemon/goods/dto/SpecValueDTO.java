package com.lemon.goods.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecValueDTO
 **/
@Data
public class SpecValueDTO {

    @NotBlank
    @Length(min = 1, max = 255)
    private String value;

    @Length(min = 1, max = 255)
    private String extend;

    @Positive
    @NotNull
    private Long specId;

}
