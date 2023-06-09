package com.lemon.goods.dto;

import com.lemon.enumeration.SaleOrNotEnum;
import com.lemon.oauth.validator.EnumValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuDTO
 **/
@Data
public class SkuDTO {
    @NotBlank
    @Length(min = 1, max = 255)
    private String title;

    @NotBlank
    @Length(min = 1, max = 255)
    private String img;

    @DecimalMin(value = "0.00")
    private BigDecimal discountPrice;

    @EnumValue(target = SaleOrNotEnum.class)
    private Integer online;

    @Positive
    @NotNull
    private Long spuId;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal price;

    @Positive
    private Integer stock;

    /**
     *  规则参数列表
     *  [
     *    {"key": "颜色", "value": "深白色", "key_id": 1, "value_id": 3},
     *    {"key": "图案", "value": "灌篮高手", "key_id": 3, "value_id": 10},
     *    {"key": "尺码", "value": "中号", "key_id": 4, "value_id": 15}
     *  ]
     *  2#1-3$3-10$4-15
     */
    @Valid
    @NotNull
    private List<SkuSelector> selectors;
}
