package com.lemon.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.goods.pojo.Theme;
import com.lemon.goods.vo.SpuSimplifyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ThemeMappler
 **/
public interface ThemeMapper extends BaseMapper<Theme> {

    Optional<Theme> findByName(String themeName);
    /**
     * 获取主题下的spu
     * @param id 主题id
     * @return spu列表
     */
    List<SpuSimplifyVO> getSpus(@Param("id") Long id);

}
