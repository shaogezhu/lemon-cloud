package com.lemon.order.dto;

import com.lemon.goods.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderDTO
 **/
@Getter
@Setter
public class OrderDTO {

    @DecimalMin(value="0.00", message = "不在合法范围内" )
    @DecimalMax(value="99999999.99", message = "不在合法范围内")
    private BigDecimal totalPrice;
    @DecimalMin(value="0.00", message = "不在合法范围内" )
    @DecimalMax(value="99999999.99", message = "不在合法范围内")
    private BigDecimal finalTotalPrice;

    private Long couponId;
    @Size(min = 1,message = "商品至少购买一件")
    private List<SkuInfoDTO> skuInfoList;

    private OrderAddressDTO address;
}
