package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Sku
 **/
@Getter
@Setter
@TableName(value = "sku",autoResultMap = true)
public class Sku extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143603L;
    @TableId
    private Long id;
    private BigDecimal price;
    @TableField("discount_price")
    private BigDecimal discountPrice;
    private Integer online;
    private String img;
    private String title;
    @TableField("spu_id")
    private Long spuId;
    @TableField("category_id")
    private Long categoryId;
    @TableField("root_category_id")
    private Long rootCategoryId;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<Spec> specs;
    private String code;
    private Integer stock;

    public BigDecimal getActualPrice() {
        return discountPrice == null ? this.price : this.discountPrice;
    }

    @JsonIgnore
    public List<String> getSpecValueList() {
        return this.getSpecs().stream().map(Spec::getValue).collect(Collectors.toList());
    }

}
