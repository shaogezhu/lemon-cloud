package com.lemon.order.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemon.entity.BaseEntity;
import com.lemon.enumeration.OrderStatus;
import com.lemon.order.dto.OrderAddressDTO;
import com.lemon.util.CommonUtil;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Order
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "`order`", autoResultMap = true)
public class Order extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143317L;
    @TableId
    private Long id;
    @TableField("order_no")
    private String orderNo;
    @TableField("user_id")
    private Long userId;
    @TableField("total_price")
    private BigDecimal totalPrice;
    @TableField("total_count")
    private Long totalCount;
    @TableField("snap_img")
    private String snapImg;
    @TableField("snap_title")
    private String snapTitle;
    @TableField("expired_time")
    private Date expiredTime;
    @TableField("placed_time")
    private Date placedTime;
    @TableField(value = "snap_items", typeHandler = FastjsonTypeHandler.class)
    private List<OrderSku> snapItems;
    @TableField(value = "snap_address", typeHandler = FastjsonTypeHandler.class)
    private OrderAddressDTO snapAddress;
    @TableField("prepay_id")
    private String prepayId;
    @TableField("final_total_price")
    private BigDecimal finalTotalPrice;
    @TableField("status")
    private Integer status;

    @JsonIgnore
    public OrderStatus getStatusEnum() {
        return OrderStatus.toType(this.status);
    }

    public Boolean needCancel() {
        if (!this.getStatusEnum().equals(OrderStatus.UNPAID)) {
            return true;
        }
        return CommonUtil.isOutOfDate(this.getExpiredTime());
    }

}
