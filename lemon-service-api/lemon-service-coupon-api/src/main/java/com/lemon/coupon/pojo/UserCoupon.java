package com.lemon.coupon.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserCoupon
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_coupon")
public class UserCoupon implements Serializable {
    private static final long serialVersionUID = 1651143446L;
    @TableId
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("coupon_id")
    private Long couponId;
    @TableField("order_id")
    private Long orderId;
    @TableField("`status`")
    private Integer status;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
}