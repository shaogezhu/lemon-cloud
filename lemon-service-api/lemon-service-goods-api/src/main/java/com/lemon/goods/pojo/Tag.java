package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Tag
 **/
@Getter
@Setter
@TableName("tag")
public class Tag extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1651143661L;
    @TableId
    private Long id;

    private String title;

    private String description;

    private Integer highlight;

    private Integer type;
}
