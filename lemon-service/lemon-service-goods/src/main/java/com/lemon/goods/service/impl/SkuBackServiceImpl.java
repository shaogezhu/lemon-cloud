package com.lemon.goods.service.impl;

import com.lemon.exception.ServerErrorException;
import com.lemon.goods.bo.SkuMessageBO;
import com.lemon.goods.mapper.SkuMapper;
import com.lemon.goods.service.SkuBackService;
import com.lemon.order.feign.OrderFeign;
import com.lemon.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuBackServiceImpl
 **/
@Service
public class SkuBackServiceImpl implements SkuBackService {
    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public void returnBack(SkuMessageBO messageBO) {
        if (messageBO.getOrderId() <= 0) {
            throw new ServerErrorException(9999);
        }
        this.cancel(messageBO.getOrderId());
    }

    private void cancel(Long oid) {
        Order order = orderFeign.getOrderById(oid);
        order.getSnapItems().forEach(i -> {
            this.skuMapper.recoverStock(i.getId(), i.getCount());
        });
    }
}
