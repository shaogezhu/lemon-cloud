package com.lemon.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.dto.CouponDTO;
import com.lemon.coupon.dto.CouponTemplateDTO;
import com.lemon.coupon.mapper.ActivityMapper;
import com.lemon.coupon.mapper.CouponMapper;
import com.lemon.coupon.mapper.CouponTemplateMapper;
import com.lemon.coupon.mapper.UserCouponMapper;
import com.lemon.coupon.pojo.Activity;
import com.lemon.coupon.pojo.Coupon;
import com.lemon.coupon.pojo.CouponTemplate;
import com.lemon.coupon.pojo.UserCoupon;
import com.lemon.coupon.service.CouponService;
import com.lemon.enumeration.CouponStatus;
import com.lemon.enumeration.CouponTypeEnum;
import com.lemon.exception.NotFoundException;
import com.lemon.exception.ParameterException;
import com.lemon.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponServiceImpl
 **/
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper,Coupon> implements CouponService {

    @Resource
    private CouponMapper couponMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private UserCouponMapper userCouponMapper;

    @Override
    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponMapper.getByCategoryId(cid,now);
    }

    @Override
    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponMapper.getWholeStoreCoupons(true,now);
    }

    @Override
    public void collectOneCoupon(Long uid, Long couponId) {
        this.couponMapper
                .findById(couponId)
                .orElseThrow(() -> new NotFoundException(40003));
        Activity activity = this.activityMapper
                .findByCouponId(couponId)
                .orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
        if (!isIn) {
            throw new ParameterException(40005);
        }
        userCouponMapper.findOneByUserIdAndCouponId(uid,couponId)
                .ifPresent((uc) -> {
                    throw new ParameterException(40006);
                });
        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        userCouponMapper.insert(userCouponNew);
    }

    @Override
    public List<Coupon> getMyAvailableCoupons(Long uid) {
        Date now = new Date();
        return couponMapper.findMyAvailable(uid,now);
    }

    @Override
    public List<Coupon> getMyUsedCoupons(Long uid) {
        return couponMapper.getMyUsedCoupons(uid);
    }

    @Override
    public List<Coupon> getMyExpiredCoupons(Long uid) {
        Date now = new Date();
        return couponMapper.getMyExpiredCoupons(uid,now);
    }

    @Autowired
    private CouponTemplateMapper couponTemplateMapper;


    @Override
    public void create(CouponDTO dto) {
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new com.lemon.exception.ParameterException(100001);
        }
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(dto, coupon);
        coupon.setRate(dto.getDiscount());
        this.save(coupon);
    }

    @Override
    public void update(CouponDTO dto, Long id) {
        Coupon coupon = this.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100001);
        }
        BeanUtils.copyProperties(dto, coupon);
        coupon.setRate(dto.getDiscount());
        this.updateById(coupon);
    }

    @Override
    public void delete(Long id) {
        Coupon coupon = this.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public List<CouponTemplate> getTemplates() {
        return couponTemplateMapper.selectList(null);
    }

    @Override
    public void createTemplate(CouponTemplateDTO dto) {
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100002);
        }
        CouponTemplate couponTemplate = new CouponTemplate();
        BeanUtils.copyProperties(dto, couponTemplate);
        couponTemplateMapper.insert(couponTemplate);
    }

    @Override
    public void updateTemplate(CouponTemplateDTO dto, Long id) {
        CouponTemplate couponTemplate = getTemplate(id);
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100001);
        }
        BeanUtils.copyProperties(dto, couponTemplate);
        couponTemplateMapper.updateById(couponTemplate);
    }

    @Override
    public CouponTemplate getTemplate(Long id) {
        CouponTemplate couponTemplate = couponTemplateMapper.selectById(id);
        if (couponTemplate == null) {
            throw new NotFoundException(100000);
        }
        return couponTemplate;
    }

    @Override
    public void deleteTemplate(Long id) {
        this.getTemplate(id);
        couponTemplateMapper.deleteById(id);
    }

    @Override
    public List<Coupon> getListByActivityId(Long id) {
        QueryWrapper<Coupon> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Coupon::getActivityId, id);
        return this.getBaseMapper().selectList(wrapper);
    }

    /**
     * 校验优惠卷数据是否满足优惠卷类型
     */
    private boolean checkCouponType(CouponTemplateDTO dto) {
        if (dto.getType() == CouponTypeEnum.FULL_MONEY_CUT.getValue()) {
            return (dto.getFullMoney() != null && dto.getMinus() != null);
        } else if (dto.getType() == CouponTypeEnum.DISCOUNT.getValue()&&dto.getDiscount() != null) {
            return true;
        } else if (dto.getType() == CouponTypeEnum.ALL.getValue()) {
            return true;
        } else if (dto.getType() == CouponTypeEnum.FULL_MONEY_DISCOUNT.getValue()) {
            return (dto.getFullMoney() != null && dto.getDiscount() != null);
        } else {
            return false;
        }
    }
}
