package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuSpec
 **/
@Data
@TableName("sku_spec")
public class SkuSpec {

    @TableId
    private Long id;

    private Long spuId;

    private Long skuId;

    private Long keyId;

    private Long valueId;

}