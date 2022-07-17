package com.lemon.coupon.feign;

import com.lemon.coupon.bo.CouponMessageBO;
import com.lemon.coupon.pojo.Coupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponFeign
 **/
@FeignClient(name = "coupon", path = "/coupon",contextId = "couponFeign")
public interface CouponFeign {
    @GetMapping("/coupon/id")
    Coupon getCouponById(@RequestParam(name = "id") Long id);
    @PostMapping("/return/back")
    void returnBack(@RequestBody CouponMessageBO couponMessageBO);
}
