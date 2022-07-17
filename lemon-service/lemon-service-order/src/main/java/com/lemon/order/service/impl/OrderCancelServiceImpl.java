package com.lemon.order.service.impl;

import com.lemon.exception.ServerErrorException;
import com.lemon.order.bo.OrderMessageBO;
import com.lemon.order.mapper.OrderMapper;
import com.lemon.order.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderCancelServiceImpl
 **/
@Service
public class OrderCancelServiceImpl implements OrderCancelService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(OrderMessageBO messageBO) {
        if (messageBO.getOrderId() <= 0) {
            throw new ServerErrorException(9999);
        }
        this.cancel(messageBO.getOrderId());
    }

    private void cancel(Long oid) {
        int res = orderMapper.cancelOrder(oid);
        if (res != 1) {
            return;
        }
    }
}
