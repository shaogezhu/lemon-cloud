package com.lemon.order.logic;

import com.lemon.exception.ParameterException;
import com.lemon.goods.bo.SkuOrderBO;
import com.lemon.goods.dto.SkuInfoDTO;
import com.lemon.goods.pojo.Sku;
import com.lemon.order.dto.OrderDTO;
import com.lemon.order.pojo.OrderSku;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderChecker
 **/

public class OrderChecker {

    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxSkuLimit;

    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>();

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList,
                        CouponChecker couponChecker, Integer maxSkuLimit){
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    public String getLeaderImg() {
        return  this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount() {
        return this.orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * 校验这个订单是否有问题
     */
    public void isOK() {
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();

        this.skuNotOnSale(orderDTO.getSkuInfoList().size(), this.serverSkuList.size());

        for (int i = 0; i < this.serverSkuList.size(); i++) {
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().get(i);
            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku, skuInfoDTO);
            this.beyondMaxSkuLimit(skuInfoDTO);

            serverTotalPrice = serverTotalPrice.add(this.calculateSkuOrderPrice(sku, skuInfoDTO));
            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));
            this.orderSkuList.add(getOrderSku(sku, skuInfoDTO));
        }

        this.totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);

        if (this.couponChecker != null) {
            this.couponChecker.isOk();
            this.couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    private OrderSku getOrderSku(Sku sku, SkuInfoDTO skuInfoDTO) {
        return OrderSku.builder()
                .id(sku.getId())
                .spuId(sku.getSpuId())
                .singlePrice(sku.getActualPrice())
                .finalPrice(sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount())))
                .count(skuInfoDTO.getCount())
                .img(sku.getImg())
                .title(sku.getTitle())
                .specValues(sku.getSpecValueList())
                .build();
    }


    /**
     * 计算使用优惠卷之前的价格是否一样
     * @param orderTotalPrice 前端传入的价格
     * @param serverTotalPrice 后端计算的价格
     */
    private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
        if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50005);
        }
    }

    /**
     * 计算sku订单的价格
     * @param sku sku
     * @param skuInfoDTO skuInfoDTO
     * @return 价格
     */
    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO){
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    /**
     * 校验前端传来的sku数量是否正确
     * @param count1 前端传来的数量
     * @param count2 后端计算的数量
     */
    private void skuNotOnSale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50002);
        }
    }

    /**
     * 判断该sku是否还有库存
     * @param sku sku
     */
    private void containsSoldOutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50001);
        }
    }

    /**
     * 购买某个sku的数量是否超过库存量
     * @param sku sku
     * @param skuInfoDTO skuInfoDTO
     */
    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (sku.getStock() < skuInfoDTO.getCount()) {
            throw new ParameterException(50003);
        }
    }

    /**
     * 是否超过某个sku限制的购买数量(比如:某个商品没人限购5件)
     * @param skuInfoDTO skuInfoDTO
     */
    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > this.maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }

}
