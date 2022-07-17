package com.lemon.order.money;

import java.math.BigDecimal;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName IMoneyDiscount
 **/
public interface IMoneyDiscount {
    /**
     * 计算订单打折后的价格，注意四舍五入、四舍六入、直接进一
     * @param original 订单的原价
     * @param discount 打多少折
     * @return 打折后的价格
     */
    BigDecimal discount(BigDecimal original, BigDecimal discount);
}