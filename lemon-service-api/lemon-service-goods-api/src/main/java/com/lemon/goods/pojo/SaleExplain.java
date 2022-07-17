package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SaleExplain
 **/
@Getter
@Setter
@TableName("sale_explain")
public class SaleExplain extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143602L;
    @TableId
    private Long id;

    private Boolean fixed;

    private String text;

    @TableField("spu_id")
    private Long spuId;
    @TableField("`index`")
    private Long index;

    @TableField("replace_id")
    private Long replaceId;

}
