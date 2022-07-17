package com.lemon.goods.feign.fallback;

import com.lemon.goods.bo.SkuMessageBO;
import com.lemon.goods.feign.SkuFeign;
import com.lemon.goods.pojo.Sku;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuFeignFallback
 **/
@Component
public class SkuFeignFallback implements SkuFeign {
    @Override
    public List<Sku> getSkuListByIds(Long[] skuIdList) {
        return new ArrayList<>();
    }

    @Override
    public Integer reduceStore(Long sid, Long count) {
        return -1;
    }

    @Override
    public void returnBack(SkuMessageBO skuMessageBO) {
        System.out.println("测试 降级~");
    }
}
