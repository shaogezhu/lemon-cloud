package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ThemeSpu
 **/
@Data
@TableName("theme_spu")
public class ThemeSpu {

    @TableId
    private Long id;

    private Long themeId;

    private Long spuId;

}