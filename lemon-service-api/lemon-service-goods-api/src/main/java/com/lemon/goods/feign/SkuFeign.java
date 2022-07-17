package com.lemon.goods.feign;

import com.lemon.goods.bo.SkuMessageBO;
import com.lemon.goods.feign.fallback.SkuFeignFallback;
import com.lemon.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuFeign
 **/
@FeignClient(name = "goods", path = "/sku", contextId = "skuFeign",fallback = SkuFeignFallback.class, configuration = SkuFeignFallback.class)
public interface SkuFeign {

    @RequestMapping(value = "/list")
    List<Sku> getSkuListByIds(@RequestParam(name = "skuIdList") Long[] skuIdList);

    @RequestMapping("/reduce")
    Integer reduceStore(@RequestParam(name = "sid") Long sid, @RequestParam(name = "count") Long count);

    @PostMapping("/return/back")
    void returnBack(@RequestBody SkuMessageBO skuMessageBO);
}

