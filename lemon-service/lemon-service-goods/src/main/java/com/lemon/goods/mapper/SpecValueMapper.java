package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.Spec;
import com.lemon.goods.pojo.SpecValue;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecValueMapper
 **/
public interface SpecValueMapper extends BaseMapper<SpecValue> {

    /**
     * 根据规格名id和规格值id，获取规格名和规格值
     * @param keyId 规格名id
     * @param valueId 规格值id
     * @return SpecKeyValueDO
     */
    Spec getSpecKeyAndValueById(Long keyId, Long valueId);

}