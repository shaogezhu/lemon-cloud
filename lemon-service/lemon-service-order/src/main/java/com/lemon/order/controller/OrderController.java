package com.lemon.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.entity.LocalUser;
import com.lemon.entity.PageCounter;
import com.lemon.entity.PagingDozer;
import com.lemon.exception.NotFoundException;
import com.lemon.interceptors.ScopeLevel;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
import com.lemon.order.dto.OrderDTO;
import com.lemon.order.logic.OrderChecker;
import com.lemon.order.pojo.Order;
import com.lemon.order.service.OrderService;
import com.lemon.order.vo.OrderIdVO;
import com.lemon.order.vo.OrderPureVO;
import com.lemon.order.vo.OrderSimplifyVO;
import com.lemon.util.CommonUtil;
import com.lemon.oauth.validator.DateTimeFormat;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderController
 **/
@RestController
@Validated
@PermissionModule("订单")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${lemon.order.pay-time-limit}")
    private Long payTimeLimit;

    @ScopeLevel
    @PostMapping("")
    public ResponseEntity<OrderIdVO> placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUserId();
        //1.商品无货
        //2.商品最大购买数量限制
        //3.SKU 单品每个用户限购数量5or1
        //4.totalPrice   finalPrice
        //5.是否拥有这个优惠卷
        //6.这个优惠券是否过期
        OrderChecker orderChecker = this.orderService.isOk(uid, orderDTO);
        Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return ResponseEntity.ok(new OrderIdVO(oid));
    }

    @ScopeLevel
    @GetMapping("/detail/{id}")
    public ResponseEntity<OrderPureVO> getOrderDetail(@PathVariable(name = "id") Long oid) {
        Optional<Order> orderOptional = this.orderService.getOrderDetail(oid);
        return ResponseEntity.ok(orderOptional.map((o) -> new OrderPureVO(o, payTimeLimit))
                .orElseThrow(() -> new NotFoundException(50009)));
    }

    @ScopeLevel
    @GetMapping("/status/unpaid")
    @SuppressWarnings("unchecked")  //忽略警告注解
    public ResponseEntity<PagingDozer> getUnpaid(@RequestParam(defaultValue = "0")
                                                 Integer start,
                                                 @RequestParam(defaultValue = "20")
                                                 Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit));
        return ResponseEntity.ok(pagingDozer);
    }

    @ScopeLevel
    @GetMapping("/by/status/{status}")
    @SuppressWarnings("unchecked")
    public ResponseEntity<PagingDozer<Order, OrderSimplifyVO>> getByStatus(@PathVariable int status,
                                                                   @RequestParam(name = "start", defaultValue = "0")
                                                                           Integer start,
                                                                   @RequestParam(name = "count", defaultValue = "10")
                                                                           Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> paging = this.orderService.getByStatus(status, page.getPage(), page.getCount());
        PagingDozer<Order, OrderSimplifyVO> pagingDozer = new PagingDozer<>(paging, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(o -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit));
        return ResponseEntity.ok(pagingDozer);
    }

    @RequestMapping("/deal")
    public ResponseEntity.BodyBuilder updateOrderStatusById(String orderNo) {
        orderService.deal(orderNo);
        return ResponseEntity.ok();
    }

    @RequestMapping("/update/prepay")
    public ResponseEntity.BodyBuilder updateOrderPrepayId(Long orderId, String payId) {
        orderService.updateOrderPrepayId(orderId, payId);
        return ResponseEntity.ok();
    }

    @RequestMapping("/order/detail")
    public ResponseEntity<Order> getOrderByUserId(@RequestParam(name = "oid") Long oid,
                                                  @RequestParam(name = "uid") Long uid) {
        Order or = orderService.getOrderById(oid, uid);
        return ResponseEntity.ok(or);
    }


    @RequestMapping("/cancel/detail")
    public ResponseEntity<Order> getOrderById(@RequestParam(name = "oid") Long oid){
        return ResponseEntity.ok(orderService.getById(oid));
    }


    @PutMapping("/status")
    @GroupRequired
    @PermissionMeta(value = "修改订单状态")
    public ResponseEntity.BodyBuilder update(
            @RequestParam(name = "id") @Positive Long id,
            @RequestParam(name = "status") @Min(value = 0) Integer status
    ) {
        orderService.changeOrderStatus(id, status);
        return ResponseEntity.ok();
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ResponseEntity<Order> getDetail(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        Order order = orderService.getById(id);
        if (order == null) {
            throw new NotFoundException(110000);
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<OrderSimplifyVO>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        IPage<Order> iPage = orderService.getOrderByPage(count, page);
        List<OrderSimplifyVO> orderSimplifyVOList = orderService.convertFromDO(iPage.getRecords());
        return ResponseEntity.ok(PageUtil.build(iPage, orderSimplifyVOList));
    }

    @GetMapping("/search")
    @LoginRequired
    public ResponseEntity<PageResponseVO<OrderSimplifyVO>> search(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end
    ) {
        IPage<Order> iPage = orderService.search(page, count, keyword, start, end);
        List<OrderSimplifyVO> orderSimplifyVOList = orderService.convertFromDO(iPage.getRecords());
        return ResponseEntity.ok(PageUtil.build(iPage, orderSimplifyVOList));
    }

}
