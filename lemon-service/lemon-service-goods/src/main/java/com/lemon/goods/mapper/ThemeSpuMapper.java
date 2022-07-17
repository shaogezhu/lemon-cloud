package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.pojo.ThemeSpu;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ThemeSpuMapper
 **/
public interface ThemeSpuMapper extends BaseMapper<ThemeSpu> {

    /**
     * 获取指定专题下可选spu列表
     * @param id 专题id
     * @return SpuDO
     */
    List<Spu> getSimplifySpus(Long id);

}
