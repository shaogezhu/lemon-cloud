package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.SpuDetailImg;
import org.apache.ibatis.annotations.Param;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuDetailImgMapper
 **/
public interface SpuDetailImgMapper extends BaseMapper<SpuDetailImg> {

    /**
     * 物理删除spu详情图
     * @param spuId Integer
     */
    void hardDeleteImgsBySpuId(@Param("spuId") Long spuId);

}
