package com.lemon.coupon.vo;

import com.lemon.coupon.pojo.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName CategoriesAllVO
 * @author shaogezhu
 * @version 1.0.0
 **/
@Getter
@Setter
public class CategoriesAllVO {
    private List<CategoryPureVO> roots;
    private List<CategoryPureVO> subs;

    public CategoriesAllVO(Map<Integer, List<Category>> map) {
        this.roots = map.get(1).stream()
                .map(CategoryPureVO::new)
                .collect(Collectors.toList());
        this.subs = map.get(2).stream()
                .map(CategoryPureVO::new)
                .collect(Collectors.toList());
    }
}
