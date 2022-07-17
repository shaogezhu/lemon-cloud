package com.lemon.coupon.bo;

import com.lemon.coupon.pojo.Banner;
import com.lemon.coupon.pojo.BannerItem;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerWithItemsBO
 **/
@Data
@NoArgsConstructor
public class BannerWithItemsBO {

    private Integer id;

    private String name;

    private String title;

    private String img;

    private String description;

    List<BannerItem> items;

    public BannerWithItemsBO(Banner banner, List<BannerItem> items) {
        BeanUtils.copyProperties(banner, this);
        this.setItems(items);
    }
}