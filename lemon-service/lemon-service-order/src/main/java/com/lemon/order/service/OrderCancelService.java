package com.lemon.order.service;

import com.lemon.order.bo.OrderMessageBO;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderCancelService
 **/
public interface OrderCancelService {
    /**
     * 用户超时未支付订单回滚
     * @param messageBO rediskey信息
     */
    void cancel(OrderMessageBO messageBO);
}
