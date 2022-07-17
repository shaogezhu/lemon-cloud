package com.lemon.order.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderSimplifyVO
 **/
@Getter
@Setter
public class OrderSimplifyVO {
    private Long id;
    private String orderNo;

    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    private BigDecimal finalTotalPrice;
    private Integer status;
    private Date expiredTime;
    private Date placedTime;
    private Long period;

    private Long userId;
    private String prepayId;
    private Boolean expired;
}