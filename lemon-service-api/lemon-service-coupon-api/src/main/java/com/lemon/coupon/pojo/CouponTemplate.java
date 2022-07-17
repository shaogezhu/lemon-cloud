package com.lemon.coupon.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponTemplate
 **/
@Data
@TableName("coupon_template")
public class CouponTemplate extends BaseEntity {

    @TableId
    private Long id;

    private String title;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    /**
     * 国内多是打折，国外多是百分比 off
     */
    private BigDecimal discount;

    /**
     * 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券
     */
    private Integer type;

}
