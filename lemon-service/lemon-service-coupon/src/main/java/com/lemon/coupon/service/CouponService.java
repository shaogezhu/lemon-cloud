package com.lemon.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.coupon.dto.CouponDTO;
import com.lemon.coupon.dto.CouponTemplateDTO;
import com.lemon.coupon.pojo.Coupon;
import com.lemon.coupon.pojo.CouponTemplate;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponService
 **/
public interface CouponService extends IService<Coupon> {
    /**
     * 查询当前分类下的所有优惠券
     * @param cid 分类id
     */
    List<Coupon> getByCategory(Long cid);

    List<Coupon> getWholeStoreCoupons();

    /**
     * 用户领取优惠卷
     * @param uid 用户id
     * @param id 优惠券id
     */
    void collectOneCoupon(Long uid, Long id);

    /**
     * 获取用户可以使用的优惠券
     * @param uid 用户id
     */
    List<Coupon> getMyAvailableCoupons(Long uid);

    /**
     * 获取用户已经使用过的优惠券
     * @param uid 用户id
     */
    List<Coupon> getMyUsedCoupons(Long uid);

    /**
     * 获取当前用户过期的优惠券
     * @param uid 用户id
     */
    List<Coupon> getMyExpiredCoupons(Long uid);


    public void create(CouponDTO dto);

    public void update(CouponDTO dto, Long id);

    public void delete(Long id);

    public List<CouponTemplate> getTemplates();

    public void createTemplate(CouponTemplateDTO dto);

    public void updateTemplate(CouponTemplateDTO dto, Long id);

    public CouponTemplate getTemplate(Long id);

    public void deleteTemplate(Long id);

    public List<Coupon> getListByActivityId(Long id);
}
