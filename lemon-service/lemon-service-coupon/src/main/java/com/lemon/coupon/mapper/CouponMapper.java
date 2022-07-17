package com.lemon.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.coupon.pojo.Coupon;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponMapper
 **/
public interface CouponMapper extends BaseMapper<Coupon> {
    List<Coupon> getByCategoryId(Long cid, Date now);

    List<Coupon> getWholeStoreCoupons(Boolean isWholeStore, Date now);

    Optional<Coupon> findById(Long couponId);

    List<Coupon> findMyAvailable(Long uid, Date now);

    List<Coupon> getMyUsedCoupons(Long uid);

    List<Coupon> getMyExpiredCoupons(Long uid, Date now);

    List<Coupon> findListByActivityId(Long activityId);

    List<Long> getCouponsByActivityId(Long id);
}
