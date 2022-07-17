package com.lemon.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.dto.SkuDTO;
import com.lemon.goods.pojo.Sku;
import com.lemon.goods.vo.SkuDetailVO;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuService
 **/
public interface SkuService extends IService<Sku> {
    /**
     * 扣减商品的库存
     * @param sid 商品id
     * @param count 数量
     */
    Integer reduceStockBySkuId(Long sid, Integer count);

    void create(SkuDTO dto);

    void update(SkuDTO dto, Long id);

    void delete(Long id);

    SkuDetailVO getDetail(Long id);
}
