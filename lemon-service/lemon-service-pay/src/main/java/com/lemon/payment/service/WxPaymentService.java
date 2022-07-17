package com.lemon.payment.service;

import com.github.wxpay.sdk.WXPay;

import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName WxPaymentService
 **/
public interface WxPaymentService {
    Map<String, String> preOrder(Long oid);

    WXPay assembleWxPayConfig();
}
