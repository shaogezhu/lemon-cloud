package com.lemon.coupon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.coupon.dto.ActivityDTO;
import com.lemon.coupon.pojo.Activity;
import com.lemon.coupon.pojo.UserCoupon;
import com.lemon.coupon.service.ActivityService;
import com.lemon.coupon.service.UserCouponService;
import com.lemon.coupon.vo.ActivityCouponVO;
import com.lemon.coupon.vo.ActivityDetailVO;
import com.lemon.coupon.vo.ActivityPureVO;
import com.lemon.coupon.vo.CouponPureVO;
import com.lemon.entity.LocalUser;
import com.lemon.exception.NotFoundException;
import com.lemon.interceptors.ScopeLevel;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ActivityController
 **/
@RestController
@Validated
@RequestMapping("/activity")
@PermissionModule("活动")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserCouponService userCouponService;

    @GetMapping("/name/{name}")
    public ResponseEntity<ActivityPureVO> getHomeActivity(@PathVariable @NotBlank String name) {
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        return ResponseEntity.ok(new ActivityPureVO(activity));
    }

    @ScopeLevel
    @GetMapping("/name/{name}/with_coupon")
    public ResponseEntity<ActivityCouponVO> getActivityWithCoupons(@PathVariable @NotBlank String name) {
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        ActivityCouponVO activityCouponVO = new ActivityCouponVO(activity);
        setUserCoupon(activityCouponVO.getCoupons());
        return ResponseEntity.ok(activityCouponVO);
    }

    private  <T extends CouponPureVO> void setUserCoupon(List<T> coupons) {
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
    @PermissionMeta("创建活动")
    @GroupRequired
    @Logger(template = "{user.username}创建了，activity活动")
    public ResponseEntity<CreatedVO> create(@RequestBody @Validated ActivityDTO dto) {
        activityService.create(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新活动")
    @GroupRequired
    @Logger(template = "{user.username}更新了，activity活动")
    public ResponseEntity<UpdatedVO> update(
            @RequestBody @Validated ActivityDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Long id) {
        activityService.update(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除活动")
    @GroupRequired
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        Activity activity = activityService.getById(id);
        if (activity == null) {
            throw new com.lemon.exception.NotFoundException(90000);
        }
        activityService.getBaseMapper().deleteById(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ResponseEntity<ActivityDetailVO> get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return ResponseEntity.ok(activityService.getDetailById(id));
    }

    @GetMapping("/page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<Activity>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<Activity> pager = new Page<>(page, count);
        IPage<Activity> paging = activityService.getBaseMapper().selectPage(pager, null);
        return ResponseEntity.ok(PageUtil.build(paging));
    }

}