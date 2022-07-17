package com.lemon.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.coupon.dto.BannerItemDTO;
import com.lemon.coupon.pojo.BannerItem;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerItemService
 **/
public interface BannerItemService  extends IService<BannerItem> {
    void delete(Long id);

    void update(BannerItemDTO dto, Long id);
}
