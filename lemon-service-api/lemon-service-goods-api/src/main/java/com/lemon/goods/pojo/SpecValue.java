package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Data;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecValue
 **/
@Data
@TableName("spec_value")
public class SpecValue extends BaseEntity {
    @TableId
    private Long id;

    private String value;

    private Long specId;

    private String extend;

}