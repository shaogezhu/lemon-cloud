package com.lemon.order.manager.redis;

import com.lemon.coupon.bo.CouponMessageBO;
import com.lemon.coupon.feign.CouponFeign;
import com.lemon.goods.bo.SkuMessageBO;
import com.lemon.goods.feign.SkuFeign;
import com.lemon.order.bo.OrderMessageBO;
import com.lemon.order.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName TopicMessageListener
 **/
@Component
public class TopicMessageListener implements MessageListener {

    @Autowired
    private OrderCancelService orderCancelService;

    @Autowired
    private CouponFeign couponFeign;

    @Autowired
    private SkuFeign skuFeign;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        String expiredKey = new String(body);
        OrderMessageBO orderMessageBO = new OrderMessageBO(expiredKey);
        CouponMessageBO couponMessageBO = new CouponMessageBO(expiredKey);
        SkuMessageBO skuMessageBO = new SkuMessageBO(expiredKey);
        orderCancelService.cancel(orderMessageBO);
        couponFeign.returnBack(couponMessageBO);
        skuFeign.returnBack(skuMessageBO);
    }
}

