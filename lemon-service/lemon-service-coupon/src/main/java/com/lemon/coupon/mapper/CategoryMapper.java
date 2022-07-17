package com.lemon.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.coupon.pojo.Category;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CategoryMapper
 **/
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> findCategoryListByActivityId(Long activityId);

    List<Category> findCategoryListByCouponId(Long couponId);
}
