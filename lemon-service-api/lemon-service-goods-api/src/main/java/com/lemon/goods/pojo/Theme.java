package com.lemon.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Theme
 **/
@Getter
@Setter
@TableName("theme")
public class Theme extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143661L;
    @TableId
    private Long id;
    private String title;
    private String description;
    private String name;
    private String extend;
    @TableField("entrance_img")
    private String entranceImg;
    @TableField("internal_top_img")
    private String internalTopImg;
    private Integer online;
    @TableField("title_img")
    private String titleImg;
    @TableField("tpl_name")
    private String tplName;

    @TableField(exist = false)
    private List<Spu> spuList;
}
