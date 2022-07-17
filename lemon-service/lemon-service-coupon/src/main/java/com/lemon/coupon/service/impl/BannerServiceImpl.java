package com.lemon.coupon.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.bo.BannerWithItemsBO;
import com.lemon.coupon.dto.BannerDTO;
import com.lemon.coupon.mapper.BannerItemMapper;
import com.lemon.coupon.mapper.BannerMapper;
import com.lemon.coupon.pojo.Banner;
import com.lemon.coupon.pojo.BannerItem;
import com.lemon.coupon.service.BannerService;
import com.lemon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerServiceImpl
 **/
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    @Resource
    private BannerMapper bannerMapper;
    @Override
    public Banner getByName(String name) {
        return bannerMapper.selectOneByName(name);
    }

    @Autowired
    private BannerItemMapper bannerItemMapper;

    @Override
    public BannerWithItemsBO getWithItems(Long id) {
        Banner banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }
        List<BannerItem> bannerItems =
                new LambdaQueryChainWrapper<>(bannerItemMapper)
                        .eq(BannerItem::getBannerId, id)
                        .list();

        return new BannerWithItemsBO(banner, bannerItems);
    }

    @Override
    public void delete(Long id) {
        Banner banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public void update(BannerDTO dto, Long id) {
        Banner bannerDO = this.getById(id);
        if (bannerDO == null) {
            throw new NotFoundException(20000);
        }
        BeanUtils.copyProperties(dto, bannerDO);
        this.updateById(bannerDO);
    }
}
