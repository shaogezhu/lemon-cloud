package com.lemon.coupon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.dto.BannerItemDTO;
import com.lemon.coupon.mapper.BannerItemMapper;
import com.lemon.coupon.pojo.BannerItem;
import com.lemon.coupon.service.BannerItemService;
import com.lemon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerItemServiceImpl
 **/
@Service
public class BannerItemServiceImpl  extends ServiceImpl<BannerItemMapper, BannerItem> implements BannerItemService {
    @Override
    public void delete(Long id) {
        BannerItem bannerItem = this.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public void update(BannerItemDTO dto, Long id) {
        BannerItem bannerItem = this.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        BeanUtils.copyProperties(dto, bannerItem);
        this.updateById(bannerItem);
    }
}
