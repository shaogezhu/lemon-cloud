package com.lemon.coupon.feign;

import com.lemon.coupon.pojo.UserCoupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserCouponFeign
 **/
@FeignClient(name = "coupon", path = "/user_coupon",contextId = "userCouponFeign")
public interface UserCouponFeign {
    @RequestMapping("/off")
    Integer writeOffCoupon(@RequestParam(name = "couponId") Long couponId,
                           @RequestParam(name = "oid") Long oid,
                           @RequestParam(name = "uid") Long uid);

    @RequestMapping("/check")
    UserCoupon checkUserCoupon(@RequestParam(name = "couponId") Long couponId,
                               @RequestParam(name = "status") Integer status,
                               @RequestParam(name = "uid") Long uid);
}
