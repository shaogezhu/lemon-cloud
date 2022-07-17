package com.lemon.coupon.vo;

import com.lemon.coupon.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName CategoryPureVO
 * @author shaogezhu
 * @version 1.0.0
 **/
@Getter
@Setter
@NoArgsConstructor
public class CategoryPureVO {
    private Long id;

    private String name;

    private Boolean isRoot;

    private String img;

    private Long parentId;

    private Long index;

    public CategoryPureVO(Category category) {
        BeanUtils.copyProperties(category, this);
    }
}
