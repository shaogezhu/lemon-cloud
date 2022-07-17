package com.lemon.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.dto.CategoryDTO;
import com.lemon.coupon.mapper.CategoryMapper;
import com.lemon.coupon.pojo.Category;
import com.lemon.coupon.service.CategoryService;
import com.lemon.enumeration.CategoryRootOrNotEnum;
import com.lemon.exception.ForbiddenException;
import com.lemon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CategoryServiceImpl
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Map<Integer, List<Category>> getCategoryAll() {
        List<Category> roots = categoryMapper.selectList(new QueryWrapper<Category>().eq("is_root",1).orderByAsc("`index`"));
        List<Category> subs = categoryMapper.selectList(new QueryWrapper<Category>().eq("is_root",0).orderByAsc("`index`"));
        Map<Integer, List<Category>> categories = new HashMap<>();
        categories.put(1, roots);
        categories.put(2, subs);
        return categories;
    }


    @Override
    public void updateCategory(CategoryDTO dto, Long id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new NotFoundException(40000);
        }
        BeanUtils.copyProperties(dto, category);
        this.updateById(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new NotFoundException(40000);
        }
        if (category.getIsRoot()== CategoryRootOrNotEnum.ROOT.getValue()) {
            // 查找当前父分类下有无子分类，如有则不能删除
            QueryWrapper<Category> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Category::getParentId, id);
            wrapper.lambda().eq(Category::getIsRoot, CategoryRootOrNotEnum.NOT_ROOT.getValue());
            wrapper.last("limit 1");
            Category subCategory = this.baseMapper.selectOne(wrapper);
            if (subCategory != null) {
                throw new ForbiddenException(40001);
            }
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new NotFoundException(40000);
        }
        return category;
    }

    @Override
    public IPage<Category> getCategoriesByPage(Integer count, Integer page, Long root) {
        Page<Category> pager = new Page<>(page, count);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getIsRoot, root);
        return this.getBaseMapper().selectPage(pager, wrapper);
    }

    @Override
    public IPage<Category> getSubCategoriesByPage(Integer count, Integer page, Long id) {
        Page<Category> pager = new Page<>(page, count);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getIsRoot, CategoryRootOrNotEnum.NOT_ROOT.getValue());
        wrapper.lambda().eq(Category::getParentId, id);
        return this.getBaseMapper().selectPage(pager, wrapper);
    }
}
