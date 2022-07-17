package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.goods.mapper.SkuSpecMapper;
import com.lemon.goods.pojo.SkuSpec;
import com.lemon.goods.service.SkuSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuSpecServiceImpl
 **/
@Service
public class SkuSpecServiceImpl extends ServiceImpl<SkuSpecMapper, SkuSpec> implements SkuSpecService {
    @Autowired
    private SkuSpecMapper skuSpecMapper;
    @Override
    public void deleteSpecs(Long spuId, Long id) {
        skuSpecMapper.deleteSpecs(spuId,id);
    }

    @Override
    public Long getSpecValueId(Long keyId, Long skuId) {
        return skuSpecMapper.getSpecValueId(keyId, skuId);
    }
}
