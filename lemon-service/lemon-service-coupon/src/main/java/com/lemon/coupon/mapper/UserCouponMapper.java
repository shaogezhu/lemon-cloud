package com.lemon.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.coupon.pojo.UserCoupon;

import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserCouponMapper
 **/
public interface UserCouponMapper extends BaseMapper<UserCoupon> {
    Optional<UserCoupon> findOneByUserIdAndCouponId(Long uid, Long couponId);

    Optional<UserCoupon> findOneByUserIdAndCouponIdAndStatus(Long uid, Long couponId, Integer status);

    int writeOff(Long couponId, Long oid, Long uid);

    int returnBack(Long couponId, Long uid);
}
