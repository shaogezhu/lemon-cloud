package com.lemon.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.coupon.bo.BannerWithItemsBO;
import com.lemon.coupon.dto.BannerDTO;
import com.lemon.coupon.pojo.Banner;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerService
 **/
public interface BannerService extends IService<Banner> {
    Banner getByName(String name);

    BannerWithItemsBO getWithItems(Long id);

    void delete(Long id);

    void update(BannerDTO dto, Long id);
}
