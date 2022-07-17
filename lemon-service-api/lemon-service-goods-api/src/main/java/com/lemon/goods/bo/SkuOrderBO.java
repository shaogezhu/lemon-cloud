package com.lemon.goods.bo;

import com.lemon.goods.dto.SkuInfoDTO;
import com.lemon.goods.pojo.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuOrderBO
 **/
@Getter
@Setter
public class SkuOrderBO {
    private BigDecimal actualPrice;
    private Integer count;
    private Long categoryId;

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
    }

    public BigDecimal getTotalPrice() {
        return this.actualPrice.multiply(new BigDecimal(this.count));
    }
}