package com.lemon.coupon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.coupon.bo.CouponMessageBO;
import com.lemon.coupon.dto.CouponDTO;
import com.lemon.coupon.dto.CouponTemplateDTO;
import com.lemon.coupon.pojo.Coupon;
import com.lemon.coupon.pojo.CouponTemplate;
import com.lemon.coupon.pojo.UserCoupon;
import com.lemon.coupon.service.CouponBackService;
import com.lemon.coupon.service.CouponService;
import com.lemon.coupon.service.UserCouponService;
import com.lemon.coupon.vo.CouponCategoryVO;
import com.lemon.coupon.vo.CouponPureVO;
import com.lemon.entity.LocalUser;
import com.lemon.enumeration.CouponStatus;
import com.lemon.exception.ParameterException;
import com.lemon.interceptors.ScopeLevel;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
import com.lemon.exception.NotFoundException;
import com.lemon.vo.CreatedVO;
import com.lemon.vo.DeletedVO;
import com.lemon.vo.UpdatedVO;
import io.github.talelin.core.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponController
 **/
@RestController
@RequestMapping("/coupon")
@PermissionModule("优惠券")
public class CouponController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private CouponBackService couponBackService;

    @ScopeLevel
    @GetMapping("/by/category/{cid}")
    public ResponseEntity<List<CouponPureVO>> getCouponListByCategory(@PathVariable Long cid) {
        List<Coupon> coupons = couponService.getByCategory(cid);
        if (coupons.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<CouponPureVO> vos = CouponPureVO.getList(coupons);
        return ResponseEntity.ok(vos);
    }
    @ScopeLevel
    @GetMapping("/whole_store")
    public ResponseEntity<List<CouponPureVO>> getWholeStoreCouponList() {
        List<Coupon> coupons = this.couponService.getWholeStoreCoupons();
        if (coupons.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<CouponPureVO> couponPureVOs = CouponPureVO.getList(coupons);
        setUserCoupon(couponPureVOs);
        return ResponseEntity.ok(couponPureVOs);
    }
    @ScopeLevel
    @PostMapping("/collect/{id}")
    public ResponseEntity.BodyBuilder collectCoupon(@PathVariable Long id) {
        Long uid =  LocalUser.getUserId();
        couponService.collectOneCoupon(uid, id);
        return ResponseEntity.ok();
    }

    @ScopeLevel
    @GetMapping("/myself/by/status/{status}")
    public ResponseEntity<List<CouponPureVO>> getMyCouponByStatus(@PathVariable Integer status) {
        Long uid =  LocalUser.getUserId();
        List<Coupon> couponList;
        //触发机制 时机 过期
        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                couponList = couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);
        }
        List<CouponPureVO> couponPureVOs = CouponPureVO.getList(couponList);
        for (CouponPureVO couponPureVO : couponPureVOs) {
            couponPureVO.setStatus(status);
        }
        return ResponseEntity.ok(couponPureVOs);
    }

    @ScopeLevel
    @GetMapping("/myself/available/with_category")
    public ResponseEntity<List<CouponCategoryVO>> getUserCouponWithCategory() {
        List<Coupon> coupons = couponService.getMyAvailableCoupons(LocalUser.getUserId());
        if (coupons.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<CouponCategoryVO> couponCategoryVOs = coupons.stream().map(CouponCategoryVO::new).collect(Collectors.toList());
        setUserCoupon(couponCategoryVOs);
        return ResponseEntity.ok(couponCategoryVOs);
    }

    @GetMapping("/coupon/id")
    public ResponseEntity<Coupon> getCouponById(Long id) {
        return ResponseEntity.ok(couponService.getById(id));
    }

    @PostMapping("/return/back")
    public void returnBack(@RequestBody CouponMessageBO couponMessageBO){
        couponBackService.returnBack(couponMessageBO);
    }

    private <T extends CouponPureVO> void setUserCoupon(List<T> coupons) {
        Long userId = LocalUser.getUserId();
        List<UserCoupon> userCoupons = userCouponService.getUserCouponListByUserId(userId);
        for (CouponPureVO coupon : coupons) {
            for (UserCoupon userCoupon : userCoupons) {
                if (coupon.getId().equals(userCoupon.getCouponId())) {
                    coupon.setStatus(userCoupon.getStatus());
                }
            }
        }
    }

    @PostMapping("")
    @PermissionMeta("创建优惠券")
    @GroupRequired
    public ResponseEntity<CreatedVO> create(@RequestBody @Validated CouponDTO dto) {
        couponService.create(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新优惠券")
    @GroupRequired
    @Logger(template = "{user.username}更新了 优惠券信息")
    public ResponseEntity<UpdatedVO> update(
            @RequestBody @Validated CouponDTO dto,
            @PathVariable @Positive(message = "{id}") Long id) {
        couponService.update(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除优惠券")
    @GroupRequired
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id}") Long id) {
        couponService.delete(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}")
    @LoginRequired
    public ResponseEntity<Coupon> get(@PathVariable @Positive(message = "{id}") Long id) {
        Coupon coupon = couponService.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<Coupon>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<Coupon> pager = new Page<>(page, count);
        IPage<Coupon> paging = couponService.getBaseMapper().selectPage(pager, null);
        return ResponseEntity.ok(PageUtil.build(paging));
    }

    @PostMapping("/template")
    @PermissionMeta("创建优惠券模板")
    @GroupRequired
    @Logger(template = "{user.username}创建了 优惠券模板")
    public ResponseEntity<CreatedVO> createTemplate(@RequestBody @Validated CouponTemplateDTO dto) {
        couponService.createTemplate(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/template/{id}")
    @PermissionMeta("更新优惠券模板")
    @GroupRequired
    @Logger(template = "{user.username}更新了 优惠券模板")
    public ResponseEntity<UpdatedVO> updateTemplate(@RequestBody @Validated CouponTemplateDTO dto,
                                    @PathVariable @Positive(message = "{id}") Long id) {
        couponService.updateTemplate(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @GetMapping("/template/{id}")
    @LoginRequired
    public ResponseEntity<CouponTemplate> getTemplate(@PathVariable @Positive(message = "{id}") Long id) {
        return ResponseEntity.ok(couponService.getTemplate(id));
    }

    @DeleteMapping("/template/{id}")
    @PermissionMeta("删除优惠券模板")
    @GroupRequired
    public ResponseEntity<DeletedVO> deleteTemplate(@PathVariable @Positive(message = "{id}") Long id) {
        couponService.deleteTemplate(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/templates")
    @LoginRequired
    public ResponseEntity<List<CouponTemplate>> templates() {
        return ResponseEntity.ok(couponService.getTemplates());
    }

    @GetMapping("/list")
    @LoginRequired
    public ResponseEntity<List<Coupon>> getListByActivityId(
            @RequestParam(name = "id") @Min(value = 1, message = "{id}") Long id) {
        return ResponseEntity.ok(couponService.getListByActivityId(id));
    }

}

