package com.lemon.coupon.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Coupon
 **/
@Getter
@Setter
@TableName("coupon")
public class Coupon extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143439L;
    @TableId
    private Long id;
    @TableField("activity_id")
    private Long activityId;
    private String title;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
    private String description;
    @TableField("full_money")
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private String remark;
    @TableField("whole_store")
    private Boolean wholeStore;
    private Integer type;

    @TableField(exist = false)
    private List<Category> categoryList;
}