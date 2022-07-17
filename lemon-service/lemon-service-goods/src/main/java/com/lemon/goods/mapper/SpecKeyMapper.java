package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.SpecKey;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecKeyMapper
 **/
public interface SpecKeyMapper extends BaseMapper<SpecKey> {

    /**
     * 根据spuId获取规格键
     * @param spuId spuId
     * @return List
     */
    List<SpecKey> getBySpuId(Long spuId);

}
