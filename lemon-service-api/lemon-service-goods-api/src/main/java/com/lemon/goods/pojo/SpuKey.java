package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuKey
 **/
@Data
@Accessors(chain = true)
@TableName("spu_key")
public class SpuKey {

    @TableId
    private Long id;

    private Long spuId;

    private Long specKeyId;

}
