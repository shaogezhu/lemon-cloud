package com.lemon.coupon.vo;

import com.lemon.coupon.pojo.Activity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ActivityCouponVO
 * @author shaogezhu
 * @version 1.0.0
 **/
@Getter
@Setter
public class ActivityCouponVO extends ActivityPureVO {
    private List<CouponPureVO> coupons;

    public ActivityCouponVO(Activity activity) {
        super(activity);
        coupons = activity.getCouponList()
                .stream().map(CouponPureVO::new)
                .collect(Collectors.toList());
    }
}