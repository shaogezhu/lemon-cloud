package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.pojo.SpuDetailImg;
import com.lemon.goods.pojo.SpuImg;
import com.lemon.goods.vo.SpuDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuMapper
 **/
public interface SpuMapper extends BaseMapper<Spu> {
    Spu getSpuDetailById(Long id);

    List<SpuImg> getSpuImgBySpuId(Long id);

    List<SpuDetailImg> getSpuDetailImgBySpuId(Long id);

    List<Spu> getSpuSimple(Long themeId);


    /**
     * 获取spu详情
     * @param id spu的id
     * @return SpuDetailDO
     */
    SpuDetailVO getDetail(Long id);

    /**
     * 获取指定spu的规格id列表
     * @param id spu的id
     * @return spu关联的规格id列表
     */
    List<Long> getSpecKeys(@Param("id") Long id);
}