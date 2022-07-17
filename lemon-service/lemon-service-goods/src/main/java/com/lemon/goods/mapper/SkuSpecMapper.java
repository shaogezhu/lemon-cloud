package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.SkuSpec;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuSpecMapper
 **/
public interface SkuSpecMapper extends BaseMapper<SkuSpec> {

    /**
     * 根据规格名id获取sku的id列表
     * @param keyId 规格名id
     * @return sku的id列表
     */
    List<Long> getSkuIdsByKeyId(Long keyId);

    /**
     * 根据规格值id获取sku的id列表
     * @param valueId 规格值id
     * @return sku的id列表
     */
    List<Long> getSkuIdsByValueId(Long valueId);

    /**
     * 根据skuId和spuId删除sku_spec记录
     * @param spuId Integer
     * @param skuId Integer
     */
    void deleteSpecs(Long spuId, Long skuId);

    /**
     * 获取已选的specId
     * @param keyId   规格键 id
     * @param skuId   sku id
     * @return 规格值id
     */
    Long getSpecValueId(Long keyId, Long skuId);

}
