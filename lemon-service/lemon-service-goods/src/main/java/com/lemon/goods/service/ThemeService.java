package com.lemon.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.dto.ThemeSpuDTO;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.pojo.Theme;
import com.lemon.goods.vo.SpuSimplifyVO;

import java.util.List;
import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ThemeService
 **/
public interface ThemeService extends IService<Theme> {
    /**
     * 根据主题的名字获取主题的信息
     * @param themeName 主题的name
     */
    Optional<Theme> findByName(String themeName);

    /**
     * 根据theme名字，查询所有的theme
     */
    List<Theme> listByIds(List<String> nameList);


    List<SpuSimplifyVO> getSpus(Long id);

    void addThemeSpu(ThemeSpuDTO dto);

    void deleteThemeSpu(Long id);

    List<Spu> getSimplifySpus(Long id);

}
