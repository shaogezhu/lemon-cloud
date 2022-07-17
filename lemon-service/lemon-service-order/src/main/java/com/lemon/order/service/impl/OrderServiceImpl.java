package com.lemon.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.feign.CouponFeign;
import com.lemon.coupon.feign.UserCouponFeign;
import com.lemon.coupon.pojo.Coupon;
import com.lemon.coupon.pojo.UserCoupon;
import com.lemon.entity.LocalUser;
import com.lemon.enumeration.CouponStatus;
import com.lemon.enumeration.OrderStatus;
import com.lemon.enumeration.OrderStatusEnum;
import com.lemon.exception.ForbiddenException;
import com.lemon.exception.NotFoundException;
import com.lemon.exception.ParameterException;
import com.lemon.exception.ServerErrorException;
import com.lemon.goods.dto.SkuInfoDTO;
import com.lemon.goods.feign.SkuFeign;
import com.lemon.goods.pojo.Sku;
import com.lemon.order.dto.OrderDTO;
import com.lemon.order.logic.CouponChecker;
import com.lemon.order.logic.OrderChecker;
import com.lemon.order.mapper.OrderMapper;
import com.lemon.order.money.IMoneyDiscount;
import com.lemon.order.pojo.Order;
import com.lemon.order.pojo.OrderSku;
import com.lemon.order.service.OrderService;
import com.lemon.order.util.OrderUtil;
import com.lemon.order.vo.OrderSimplifyVO;
import com.lemon.util.CommonUtil;
import com.lemon.util.SensitiveDataUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderServiceImpl
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private CouponFeign couponFeign;

    @Autowired
    private UserCouponFeign userCouponFeign;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Value("${lemon.order.max-sku-limit}")
    private int maxSkuLimit;

    @Value("${lemon.order.pay-time-limit}")
    private Integer payTimeLimit;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    //todo 全局事务处理待补充
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        Calendar now = Calendar.getInstance();
        Calendar now1 = (Calendar) now.clone();
        Date expiredTime = CommonUtil.addSomeSeconds(now, this.payTimeLimit).getTime();

        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .expiredTime(expiredTime)
                .placedTime(now1.getTime())
                .build();

        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());

        this.orderMapper.insert(order);
        this.reduceStock(orderChecker);
        //reduceStock 扣减库存
        //核销优惠券
        //加入到延迟消息队列
        Long couponId = -1L;
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
            couponId = orderDTO.getCouponId();
        }
        this.sendToRedis(order.getId(), uid, couponId);
        return order.getId();
    }

    private void sendToRedis(Long oid, Long uid, Long couponId) {

        String key = uid.toString() + "," + oid.toString() + "," + couponId.toString();
        try {
            stringRedisTemplate.opsForValue().set(key, "1", this.payTimeLimit, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page<Order> getUnpaid(Integer page, Integer size) {
        Page<Order> pageable = new Page<>(page, size);
        Long uid = LocalUser.getUserId();
        Date now = new Date();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", OrderStatus.UNPAID.value());
        queryWrapper.eq("user_id", uid);
        queryWrapper.gt("expired_time", now);
        queryWrapper.orderByDesc("create_time");
        return this.orderMapper.selectPage(pageable, queryWrapper);
    }

    @Override
    public Page<Order> getByStatus(Integer status, Integer page, Integer size) {
        Page<Order> pageable = new Page<>(page, size);
        Long uid = LocalUser.getUserId();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", uid);
        queryWrapper.orderByDesc("create_time");
        if (status == OrderStatus.All.value()) {
            return this.orderMapper.selectPage(pageable, queryWrapper);
        }
        queryWrapper.eq("status", status);
        return this.orderMapper.selectPage(pageable, queryWrapper);
    }

    @Override
    public Optional<Order> getOrderDetail(Long oid) {
        Long uid = LocalUser.getUserId();
        return this.orderMapper.findOneByUserIdAndId(oid, uid);
    }

    @Override
    public void updateOrderPrepayId(Long orderId, String prePayId) {
        Optional<Order> order = this.orderMapper.findOneById(orderId);
        order.ifPresent(o -> {
            this.orderMapper.updateById(Order.builder().id(o.getId()).prepayId(prePayId).build());
        });
        order.orElseThrow(() -> new ParameterException(10007));
    }


    private void writeOffCoupon(Long couponId, Long oid, Long uid) {
        int result = userCouponFeign.writeOffCoupon(couponId, oid, uid);
        if (result != 1) {
            throw new ForbiddenException(40012);
        }
    }

    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku orderSku : orderSkuList) {
            int result = skuFeign.reduceStore(orderSku.getId(), orderSku.getCount().longValue());
            if (result != 1) {
                throw new ParameterException(50003);
            }
        }
    }

    @Override
    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <= 0) {
            throw new ParameterException(50011);
        }

        List<Long> skuIdList = orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());

        List<Sku> skuList = skuFeign.getSkuListByIds(skuIdList.toArray(new Long[0]));

        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if (couponId != null) {
            Coupon coupon = couponFeign.getCouponById(couponId);
            UserCoupon userCouponResult = userCouponFeign.checkUserCoupon(couponId, CouponStatus.AVAILABLE.getValue(), uid);
            if (coupon == null || userCouponResult == null) {
                throw new NotFoundException(40004);
            }
            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }
        OrderChecker orderChecker = new OrderChecker(orderDTO, skuList, couponChecker, this.maxSkuLimit);
        orderChecker.isOK();
        return orderChecker;
    }

    @Override
    public void deal(String orderNo) {
        Optional<Order> orderOptional = this.orderMapper.findFirstByOrderNo(orderNo);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));

        int res = -1;
        if (order.getStatus().equals(OrderStatus.UNPAID.value())
                || order.getStatus().equals(OrderStatus.CANCELED.value())) {
            res = this.orderMapper.updateStatusByOrderNo(orderNo, OrderStatus.PAID.value());
        }
        if (res != 1) {
            throw new ServerErrorException(9999);
        }
    }

    @Override
    public Order getOrderById(Long oid, Long uid) {
        return orderMapper.findOneByUserIdAndId(oid, uid).orElseThrow(() -> new NotFoundException(50009));
    }


    @Override
    public void changeOrderStatus(Long id, Integer status) {
        Order order = this.getBaseMapper().selectById(id);
        if (order == null) {
            throw new NotFoundException(110000);
        }
        // 检查订单状态
        if (order.getStatus() != OrderStatusEnum.PAID.getValue() && order.getStatus() != OrderStatusEnum.DELIVERED.getValue()) {
            throw new ForbiddenException(110001);
        }
        if (order.getStatus() == OrderStatusEnum.PAID.getValue()) {
            if (status != OrderStatusEnum.DELIVERED.getValue()) {
                throw new ForbiddenException(110002);
            }
            this.getBaseMapper().changeOrderStatus(status, id);
        }
        if (order.getStatus() == OrderStatusEnum.DELIVERED.getValue()) {
            if (status != OrderStatusEnum.FINISHED.getValue()) {
                throw new ForbiddenException(110003);
            }
            this.getBaseMapper().changeOrderStatus(status, id);
        }
    }

    @Override
    public IPage<Order> getOrderByPage(Integer count, Integer page) {
        Page<Order> pager = new Page<>(page, count);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(Order::getId);
        IPage<Order> paging = this.getBaseMapper().selectPage(pager, wrapper);
        return paging;
    }

    @Override
    public IPage<Order> search(Integer page, Integer count, String keyword, Date start, Date end) {
        Page<Order> pager = new Page<>(page, count);
        IPage<Order> paging = this.baseMapper.searchOrders(pager, "%" + keyword + "%", start, end);
        return paging;
    }

    @Override
    public List<OrderSimplifyVO> convertFromDO(List<Order> orders) {
        List<OrderSimplifyVO> orderExpires = new ArrayList<>();
        orders.forEach(order -> {
            Date expireTime = order.getExpiredTime();
            OrderSimplifyVO orderSimplifyVO = new OrderSimplifyVO();
            BeanUtils.copyProperties(order, orderSimplifyVO);
            if (expireTime != null) {
                orderSimplifyVO.setExpired(expireTime.before(new Date()));
            }
            orderSimplifyVO.setPrepayId(SensitiveDataUtil.defaultHide(orderSimplifyVO.getPrepayId()));
            orderExpires.add(orderSimplifyVO);
        });
        return orderExpires;
    }

}
