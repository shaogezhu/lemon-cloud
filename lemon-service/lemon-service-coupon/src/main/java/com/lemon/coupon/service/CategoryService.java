package com.lemon.coupon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.coupon.dto.CategoryDTO;
import com.lemon.coupon.pojo.Category;

import java.util.List;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CategoryService
 **/
public interface CategoryService extends IService<Category> {
    Map<Integer, List<Category>> getCategoryAll();

    void updateCategory(CategoryDTO dto, Long id);

    void deleteCategory(Long id);

    Category getCategoryById(Long id);

    IPage<Category> getCategoriesByPage(Integer count, Integer page, Long root);

    IPage<Category> getSubCategoriesByPage(Integer count, Integer page, Long id);
}
