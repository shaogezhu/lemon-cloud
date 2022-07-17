package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.Sku;
import com.lemon.goods.vo.SkuDetailVO;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuMapper
 **/
public interface SkuMapper extends BaseMapper<Sku> {
    Integer reduceStockBySkuId(Long sid, Integer count);

    List<Sku> getSkuBySpuId(Long id);

    void recoverStock(Long sid, Integer quantity);

    /**
     * 根据 skuId 获取 sku 详情
     * @param id skuId
     * @return SkuDetailDO
     */
    SkuDetailVO getDetail(Long id);
}
