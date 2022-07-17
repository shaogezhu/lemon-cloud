package com.lemon.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.pojo.SkuSpec;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuSpecService
 **/
public interface SkuSpecService extends IService<SkuSpec> {
    void deleteSpecs(Long spuId, Long id);

    Long getSpecValueId(Long keyId, Long skuId);
}
