package com.lemon.coupon.service;

import com.lemon.coupon.bo.CouponMessageBO;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponBackService
 **/
public interface CouponBackService {
    /**
     * 退还给用户未使用成功的优惠卷
     * @param bo 订单信息
     */
    void returnBack(CouponMessageBO bo);
}
