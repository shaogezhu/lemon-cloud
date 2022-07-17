package com.lemon.coupon.dto;

import com.lemon.enumeration.CouponTypeEnum;
import com.lemon.oauth.validator.EnumValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponTemplateDTO
 **/
@Data
public class CouponTemplateDTO {
    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @Length(min = 1, max = 255)
    private String description;

    @DecimalMin(value = "0.00")
    private BigDecimal fullMoney;

    @DecimalMin(value = "0.00")
    private BigDecimal minus;

    /**f
     * 国内多是打折，国外多是百分比 off
     */
    @DecimalMin(value = "0.00")
    private BigDecimal discount;

    /**
     * 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券
     */
    @NotNull
    @EnumValue(target = CouponTypeEnum.class)
    private Integer type;
}

