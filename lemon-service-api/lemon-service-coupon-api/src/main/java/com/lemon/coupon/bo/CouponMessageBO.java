package com.lemon.coupon.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponMessageBO
 **/
@Getter
@Setter
public class CouponMessageBO {
    private Long orderId;
    private Long couponId;
    private Long userId;
    private String message;

    public CouponMessageBO(String message) {
        this.message = message;
        this.parseId(message);
    }

    private void parseId(String message) {
        String[] temp = message.split(",");
        this.userId = Long.valueOf(temp[0]);
        this.orderId = Long.valueOf(temp[1]);
        this.couponId = Long.valueOf(temp[2]);
    }

}