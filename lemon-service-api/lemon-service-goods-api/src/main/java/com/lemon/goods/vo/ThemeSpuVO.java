package com.lemon.goods.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ThemeSpuVO
 **/
@Getter
@Setter
@NoArgsConstructor
public class ThemeSpuVO extends ThemePureVO {

    private List<SpuSimplifyVO> spuList;
}
