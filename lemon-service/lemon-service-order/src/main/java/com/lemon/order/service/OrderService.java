package com.lemon.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.order.dto.OrderDTO;
import com.lemon.order.logic.OrderChecker;
import com.lemon.order.pojo.Order;
import com.lemon.order.vo.OrderSimplifyVO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderService
 **/
public interface OrderService extends IService<Order> {
    /**
     * 生成订单
     * 1.生成订单
     * 2.扣减库存
     * 3.核销优惠券
     * 4.加入到延迟消息队列
     * @param uid          用户id
     * @param orderDTO     订单信息
     * @param orderChecker 订单校验类
     * @return 订单id
     */
    Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker);

    Page<Order> getUnpaid(Integer page, Integer size);

    Page<Order> getByStatus(Integer status, Integer page, Integer size);

    Optional<Order> getOrderDetail(Long oid);

    void updateOrderPrepayId(Long orderId, String prePayId);

    /**
     * 校验订单的参数是否正确，是否合法
     * 1.sku数量是否相等
     * 2.每一种sku是否还有库存
     * 3.每一种sku的购买数量是否大于库存量
     * 4.是否超过某个sku限制的购买数量(比如:某个商品没人限购5件)
     * 5.计算使用优惠卷之前（前端穿的价格和服务端计算的价格）是否一样
     * 6.订单中使用的优惠卷是否过期
     * 7.订单中的优惠卷是否满足使用条件（满减条件是否满足）
     * 8.服务器计算订单使用后的订单价格，让后与前端传来的最终价格进行比较判断是否相等
     * @param uid      用户id
     * @param orderDTO 订单
     * @return orderChecker
     */
    OrderChecker isOk(Long uid, OrderDTO orderDTO);

    /**
     * 交易成功 更新订单状态
     * @param orderNo 订单号
     */
    void deal(String orderNo);

    /**
     * 通过订单id和用户id获取用户的订单详情信息
     * @param oid 订单id
     * @param uid 用户id
     */
    Order getOrderById(Long oid,Long uid);



    void changeOrderStatus(Long id, Integer status);

    IPage<Order> getOrderByPage(Integer count, Integer page);

    IPage<Order> search(Integer page, Integer count, String keyword, Date start, Date end);

    List<OrderSimplifyVO> convertFromDO(List<Order> orders);
}
