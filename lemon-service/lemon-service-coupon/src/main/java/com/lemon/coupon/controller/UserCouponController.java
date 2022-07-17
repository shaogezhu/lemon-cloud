package com.lemon.coupon.controller;

import com.lemon.coupon.pojo.UserCoupon;
import com.lemon.coupon.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserCouponController
 **/
@RestController
@RequestMapping("/user_coupon")
public class UserCouponController {
    @Autowired
    private UserCouponService userCouponService;

    @RequestMapping("/off")
    public ResponseEntity<Integer> writeOffCoupon(Long couponId, Long oid, Long uid) {
        return ResponseEntity.ok(userCouponService.writeOffCoupon(couponId, oid, uid));
    }

    @RequestMapping("/check")
    public ResponseEntity<UserCoupon> checkUserCoupon(Long couponId, Integer status, Long uid) {
        return ResponseEntity.ok(userCouponService.checkUserCoupon(couponId, status, uid));
    }

}
