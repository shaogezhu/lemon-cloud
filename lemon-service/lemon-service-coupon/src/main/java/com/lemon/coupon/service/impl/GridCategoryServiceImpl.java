package com.lemon.coupon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.dto.GridCategoryDTO;
import com.lemon.coupon.mapper.CategoryMapper;
import com.lemon.coupon.mapper.GridCategoryMapper;
import com.lemon.coupon.pojo.Category;
import com.lemon.coupon.pojo.GridCategory;
import com.lemon.coupon.service.GridCategoryService;
import com.lemon.exception.ForbiddenException;
import com.lemon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GridCategoryServiceImpl
 **/
@Service
public class GridCategoryServiceImpl extends ServiceImpl<GridCategoryMapper,GridCategory> implements GridCategoryService {

    @Value("${lemon.grid-category-maximum-quantity}")
    private int maximumQuality;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void createGridCategory(GridCategoryDTO dto) {
        Integer count = this.getBaseMapper().selectCount(null);
        if (count >= maximumQuality) {
            throw new ForbiddenException(50001);
        }
        Category category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null) {
            throw new NotFoundException(40000);
        }
        GridCategory gridCategory = new GridCategory();
        BeanUtils.copyProperties(dto, gridCategory);
        this.save(gridCategory);
    }

    @Override
    public void updateGridCategory(GridCategoryDTO dto, Long id) {
        GridCategory gridCategory = this.getById(id);
        if (gridCategory == null) {
            throw new NotFoundException(50000);
        }
        Category category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null) {
            throw new NotFoundException(40000);
        }
        setGridCategoryByCondition(dto, gridCategory, category);
        this.updateById(gridCategory);
    }

    @Override
    public void deleteGridCategory(Long id) {
        GridCategory gridCategory = this.getById(id);
        if (gridCategory == null) {
            throw new NotFoundException(50000);
        }
        this.getBaseMapper().deleteById(id);
    }

    private void setGridCategoryByCondition(GridCategoryDTO dto, GridCategory gridCategory, Category category) {
        // 如果存在 title，赋值 title，否则填充 name
        if (dto.getTitle() == null) {
            gridCategory.setTitle(category.getName());
        } else {
            gridCategory.setTitle(dto.getTitle());
        }
        if (dto.getName() != null) {
            gridCategory.setName(dto.getName());
        } else {
            gridCategory.setName(category.getName());
        }
        gridCategory.setImg(dto.getImg());
        // 如果当前绑定的分类无父分类，则绑定到rootCategoryId
        // 否则绑定父分类绑定到rootCategoryId，当前id绑定到categoryId
        if (category.getParentId() == null) {
            gridCategory.setRootCategoryId(category.getId());
        } else {
            gridCategory.setRootCategoryId(category.getParentId());
            gridCategory.setCategoryId(category.getId());
        }
    }
}
