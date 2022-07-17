package com.lemon.order.feign;

import com.lemon.order.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderFeign
 **/
@FeignClient(name = "order", path = "/order",contextId = "orderFeign")
public interface OrderFeign {
    @RequestMapping("/order/detail")
    Order getOrderByUserId(@RequestParam(name = "oid") Long oid,
                           @RequestParam(name = "uid") Long uid);

    @RequestMapping("/cancel/detail")
    Order getOrderById(@RequestParam(name = "oid") Long oid);

    @GetMapping("/deal")
    void deal(@RequestParam(name = "orderNo") String orderNo);

    @RequestMapping("/update/prepay")
    void updateOrderPrepayId(@RequestParam(name = "orderId") Long orderId,
                             @RequestParam(name = "payId") String payId);
}
