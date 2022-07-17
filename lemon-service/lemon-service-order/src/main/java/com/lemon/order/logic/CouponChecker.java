package com.lemon.order.logic;

import com.lemon.coupon.pojo.Category;
import com.lemon.coupon.pojo.Coupon;
import com.lemon.enumeration.CouponType;
import com.lemon.exception.ForbiddenException;
import com.lemon.exception.ParameterException;
import com.lemon.goods.bo.SkuOrderBO;
import com.lemon.order.money.IMoneyDiscount;
import com.lemon.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponChecker
 **/
public class CouponChecker {
    private Coupon coupon;

    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    /**
     * 订单中使用的优惠卷是否过期
     */
    public void isOk() {
        Date now = new Date();
        Boolean isInTimeline = CommonUtil.isInTimeLine(now, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!isInTimeline) {
            throw new ForbiddenException(40007);
        }
    }

    /**
     * 服务器计算订单使用后的订单价格，让后与前端传来的最终价格进行比较判断是否相等
     * @param orderFinalTotalPrice 前端传来的订单最终价格
     * @param serverTotalPrice 服务端计算的最终价格（还没有使用优惠卷）
     */
    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice,
                                    BigDecimal serverTotalPrice) {
        BigDecimal serverFinalTotalPrice;

        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            case FULL_OFF:
                serverFinalTotalPrice = this.iMoneyDiscount.discount(serverTotalPrice, this.coupon.getRate());
                break;
            default:
                throw new ParameterException(40009);
        }
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    /**
     * 订单中的优惠卷是否满足使用条件
     *
     * @param skuOrderBOList skuOrderBOList
     * @param serverTotalPrice serverTotalPrice
     */
    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice) {
        BigDecimal orderCategoryPrice;

        if (this.coupon.getWholeStore()) {
            orderCategoryPrice = serverTotalPrice;
        } else {
            List<Long> cidList = this.coupon.getCategoryList()
                    .stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            orderCategoryPrice = this.getSumByCategoryList(skuOrderBOList, cidList);
        }
        this.couponCanBeUsed(orderCategoryPrice);
    }

    /**
     * 判断是否满足使用优惠卷的条件
     * @param orderCategoryPrice orderCategoryPrice
     */
    private void couponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF:
            case FULL_MINUS:
                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compare > 0) {
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }

    /**
     * 计算这个订单中 这个优惠卷可以使用的范围中 商品的总价格
     * @param skuOrderBOList  订单中的sku集合
     * @param cidList 优惠卷可以使用的categoryId范围
     * @return 商品的总价格
     */
    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOList, List<Long> cidList) {
        BigDecimal sum = cidList.stream()
                .map(cid -> this.getSumByCategory(skuOrderBOList, cid))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        return sum;
    }

    /**
     *  订单中的sku哪些属于这个cid，并计算价格
     * @param skuOrderBOList 订单中的sku集合
     * @param cid 优惠卷可以使用的categoryId
     * @return 属于cid的sku的价格
     */
    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOList, Long cid) {
        BigDecimal sum = skuOrderBOList.stream()
                .filter(sku -> sku.getCategoryId().equals(cid))
                .map(SkuOrderBO::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        return sum;
    }
}