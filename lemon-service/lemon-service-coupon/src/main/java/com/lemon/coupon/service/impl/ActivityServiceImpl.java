package com.lemon.coupon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.dto.ActivityDTO;
import com.lemon.coupon.mapper.ActivityMapper;
import com.lemon.coupon.mapper.CouponMapper;
import com.lemon.coupon.pojo.Activity;
import com.lemon.coupon.service.ActivityService;
import com.lemon.coupon.vo.ActivityDetailVO;
import com.lemon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ActivityServiceImpl
 **/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;
    @Override
    public Activity getByName(String name) {
        return activityMapper.getByName(name);
    }

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public void create(ActivityDTO dto) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(dto, activity);
        this.save(activity);
    }

    @Override
    public void update(ActivityDTO dto, Long id) {
        Activity activity = this.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        BeanUtils.copyProperties(dto, activity);
        this.updateById(activity);
    }

    @Override
    public ActivityDetailVO getDetailById(Long id) {
        Activity activity = this.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        List<Long> coupons = couponMapper.getCouponsByActivityId(id);
        ActivityDetailVO activityDetail = new ActivityDetailVO();
        activityDetail.setCouponIds(coupons);
        BeanUtils.copyProperties(activity, activityDetail);
        return activityDetail;
    }
}
