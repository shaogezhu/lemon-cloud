package com.lemon.goods.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuSelector
 **/
@Data
public class SkuSelector {

    @Positive
    @NotNull
    private Long keyId;

    @Positive
    @NotNull
    private Long valueId;

}

