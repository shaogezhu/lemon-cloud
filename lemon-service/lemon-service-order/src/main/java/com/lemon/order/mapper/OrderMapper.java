package com.lemon.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.order.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderMapper
 **/
public interface OrderMapper extends BaseMapper<Order> {
    Optional<Order> findOneByUserIdAndId(Long oid, Long uid);

    Optional<Order> findOneById(Long orderId);

    Optional<Order> findFirstByOrderNo(String orderNo);

    int updateStatusByOrderNo(String orderNo, int status);

    int cancelOrder(Long oid);


    /**
     * 修改订单状态
     * @param status 状态
     * @param id 订单id
     * @return int
     */
    int changeOrderStatus(@Param("status") Integer status, @Param("id") Long id);

    /**
     * 搜索订单
     * @param pager 分页对象
     * @param keyword 关键字
     * @param start 开始时间
     * @param end 结束时间
     * @return IPage
     */
    IPage<Order> searchOrders(Page<Order> pager, String keyword, Date start, Date end);

}
