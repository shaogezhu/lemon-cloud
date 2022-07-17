package com.lemon.coupon.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Activity
 **/
@Setter
@Getter
@TableName("activity")
public class Activity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143440L;
    @TableId
    private Long id;
    private String title;
    private String name;
    private String description;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
    private Integer online;
    @TableField("entrance_img")
    private String entranceImg;
    @TableField("internal_top_img")
    private String internalTopImg;
    private String remark;

    @TableField(exist = false)
    private List<Category> categoryList;

    @TableField(exist = false)
    private List<Coupon> couponList;

}
