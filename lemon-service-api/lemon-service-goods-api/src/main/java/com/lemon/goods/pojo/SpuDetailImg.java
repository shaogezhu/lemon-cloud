package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuDetailImg
 **/
@Setter
@Getter
@Accessors(chain = true)
@TableName("spu_detail_img")
public class SpuDetailImg extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143659L;
    @TableId
    private Long id;

    private String img;

    @TableField("spu_id")
    private Long spuId;
    @TableField("`index`")
    private Integer index;
}
