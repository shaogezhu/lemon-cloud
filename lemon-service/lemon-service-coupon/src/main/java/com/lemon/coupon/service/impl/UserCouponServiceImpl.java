package com.lemon.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.mapper.UserCouponMapper;
import com.lemon.coupon.pojo.UserCoupon;
import com.lemon.coupon.service.UserCouponService;
import com.lemon.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserCouponServiceImpl
 **/
@Service
public class UserCouponServiceImpl extends ServiceImpl<UserCouponMapper, UserCoupon> implements UserCouponService {
    @Resource
    private UserCouponMapper userCouponMapper;

    @Override
    public List<UserCoupon> getUserCouponListByUserId(Long userId) {
        return userCouponMapper.selectList(new QueryWrapper<UserCoupon>().eq("user_id", userId));
    }

    @Override
    public Integer writeOffCoupon(Long couponId, Long oid, Long uid) {
        return userCouponMapper.writeOff(couponId, oid, uid);
    }

    @Override
    public UserCoupon checkUserCoupon(Long couponId, Integer status, Long uid) {
        return userCouponMapper.findOneByUserIdAndCouponIdAndStatus(uid, couponId, status)
                .orElseThrow(() -> new NotFoundException(50006));
    }


}
