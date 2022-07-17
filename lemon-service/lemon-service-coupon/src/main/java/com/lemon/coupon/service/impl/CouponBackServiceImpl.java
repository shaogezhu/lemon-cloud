package com.lemon.coupon.service.impl;

import com.lemon.coupon.bo.CouponMessageBO;
import com.lemon.coupon.mapper.UserCouponMapper;
import com.lemon.coupon.service.CouponBackService;
import com.lemon.enumeration.OrderStatus;
import com.lemon.order.feign.OrderFeign;
import com.lemon.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponBackServiceImpl
 **/
@Service
public class CouponBackServiceImpl implements CouponBackService {
    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void returnBack(CouponMessageBO bo) {
        Long couponId = bo.getCouponId();
        Long uid = bo.getUserId();
        Long orderId = bo.getOrderId();

        if (couponId == -1) {
            return;
        }

        Order order = orderFeign.getOrderByUserId(orderId,uid);

        if (order.getStatusEnum().equals(OrderStatus.UNPAID)
                || order.getStatusEnum().equals(OrderStatus.CANCELED)) {
            this.userCouponMapper.returnBack(couponId, uid);
        }
    }
}
