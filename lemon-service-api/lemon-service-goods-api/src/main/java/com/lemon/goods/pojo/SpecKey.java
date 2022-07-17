package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Data;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecKey
 **/
@Data
@TableName("spec_key")
public class SpecKey extends BaseEntity {

    @TableId
    private Long id;

    private String name;

    private String unit;

    private Integer standard;

    private String description;

}
