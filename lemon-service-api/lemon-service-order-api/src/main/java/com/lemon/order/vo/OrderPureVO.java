package com.lemon.order.vo;

import com.lemon.order.pojo.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderPureVO
 **/
@Getter
@Setter
public class OrderPureVO extends Order {
    private Long period;
    private Date createTime;

    public OrderPureVO(Order order, Long period) {
        BeanUtils.copyProperties(order, this);
        this.period = period;
    }
}