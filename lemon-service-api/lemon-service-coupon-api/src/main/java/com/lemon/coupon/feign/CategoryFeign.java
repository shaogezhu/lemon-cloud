package com.lemon.coupon.feign;

import com.lemon.coupon.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CategoryFeign
 **/
@FeignClient(name = "coupon", path = "/category",contextId = "categoryFeign")
public interface CategoryFeign {

    @RequestMapping("/id")
    Category getCategoryById(@RequestParam(name = "id") Long id);
}

