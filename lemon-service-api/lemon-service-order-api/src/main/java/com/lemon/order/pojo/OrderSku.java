package com.lemon.order.pojo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderSku
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSku implements Serializable {
    private static final long serialVersionUID = 1651143391L;
    private Long id;
    private Long spuId;
    private BigDecimal finalPrice;
    private BigDecimal singlePrice;
    private List<String> specValues;
    private Integer count;
    private String img;
    private String title;
}

