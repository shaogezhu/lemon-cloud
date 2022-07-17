package com.lemon.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.coupon.dto.GridCategoryDTO;
import com.lemon.coupon.pojo.GridCategory;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GridCategoryService
 **/
public interface GridCategoryService extends IService<GridCategory> {

    void createGridCategory(GridCategoryDTO dto);

    void updateGridCategory(GridCategoryDTO dto, Long id);

    void deleteGridCategory(Long id);

}
