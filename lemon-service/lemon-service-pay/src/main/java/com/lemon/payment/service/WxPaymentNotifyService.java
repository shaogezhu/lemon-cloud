package com.lemon.payment.service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName WxPaymentNotifyService
 **/
public interface WxPaymentNotifyService {
    void processPayNotify(String xml);
}
