package com.lemon.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.coupon.pojo.UserCoupon;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserCouponService
 **/
public interface UserCouponService extends IService<UserCoupon> {
    List<UserCoupon> getUserCouponListByUserId(Long userId);

    Integer writeOffCoupon(Long couponId, Long oid, Long uid);

    UserCoupon checkUserCoupon(Long couponId, Integer status, Long uid);
}
