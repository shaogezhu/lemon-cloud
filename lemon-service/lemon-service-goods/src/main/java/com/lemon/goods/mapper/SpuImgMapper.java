package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.SpuImg;
import org.apache.ibatis.annotations.Param;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuImgMapper
 **/
public interface SpuImgMapper extends BaseMapper<SpuImg> {

    /**
     * 物理删除spu轮播图
     * @param spuId Integer
     */
    void hardDeleteImgsBySpuId(@Param("spuId") Long spuId);

}
