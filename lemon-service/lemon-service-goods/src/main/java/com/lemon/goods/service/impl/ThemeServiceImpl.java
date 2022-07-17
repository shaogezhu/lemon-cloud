package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.goods.dto.ThemeSpuDTO;
import com.lemon.goods.mapper.ThemeMapper;
import com.lemon.goods.mapper.ThemeSpuMapper;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.pojo.Theme;
import com.lemon.goods.pojo.ThemeSpu;
import com.lemon.goods.service.ThemeService;
import com.lemon.goods.vo.SpuSimplifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ThemeServiceImpl
 **/
@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeMapper, Theme> implements ThemeService {

    @Resource
    private ThemeMapper themeMapper;

    @Autowired
    private ThemeSpuMapper themeSpuMapper;

    @Override
    public Optional<Theme> findByName(String themeName) {
        return themeMapper.findByName(themeName);
    }

    @Override
    public List<Theme> listByIds(List<String> nameList) {
        QueryWrapper<Theme> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("name",nameList);
        return themeMapper.selectList(queryWrapper);
    }
    @Override
    public List<SpuSimplifyVO> getSpus(Long id) {
        return this.getBaseMapper().getSpus(id);
    }

    @Override
    public void addThemeSpu(ThemeSpuDTO dto) {
        ThemeSpu themeSpu = new ThemeSpu();
        themeSpu.setThemeId(dto.getThemeId());
        themeSpu.setSpuId(dto.getSpuId());
        themeSpuMapper.insert(themeSpu);
    }

    @Override
    public void deleteThemeSpu(Long id) {
        themeSpuMapper.deleteById(id);
    }

    @Override
    public List<Spu> getSimplifySpus(Long id) {
        return themeSpuMapper.getSimplifySpus(id);
    }

}
