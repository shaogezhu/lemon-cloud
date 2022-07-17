package com.lemon.goods.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ThemeSpuDTO
 **/
@Data
public class ThemeSpuDTO {

    @Positive
    @NotNull
    private Long themeId;

    @Positive
    @NotNull
    private Long spuId;

}
