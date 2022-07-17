package com.lemon.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.coupon.pojo.Banner;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerMapper
 **/
public interface BannerMapper extends BaseMapper<Banner> {
    Banner selectOneByName(String name);
}
