package com.lemon.goods.vo;

import com.lemon.goods.pojo.Sku;
import lombok.Data;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuDetailVO
 **/
@Data
public class SkuDetailVO extends Sku {

    private Long spuId;

    private String spuName;

    private String currency;

}