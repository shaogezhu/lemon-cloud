package com.lemon.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.coupon.pojo.Activity;

import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ActivityMapper
 **/
public interface ActivityMapper extends BaseMapper<Activity> {
    Optional<Activity> findByCouponId(Long couponId);

    Activity getByName(String name);
}
