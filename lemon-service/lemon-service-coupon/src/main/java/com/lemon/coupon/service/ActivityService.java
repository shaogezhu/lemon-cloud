package com.lemon.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.coupon.dto.ActivityDTO;
import com.lemon.coupon.pojo.Activity;
import com.lemon.coupon.vo.ActivityDetailVO;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ActivityService
 **/
public interface ActivityService extends IService<Activity> {
    Activity getByName(String name);
    void create(ActivityDTO dto);

    void update(ActivityDTO dto, Long id);

    ActivityDetailVO getDetailById(Long id);
}
