package com.lemon.payment.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.lemon.exception.ServerErrorException;
import com.lemon.order.feign.OrderFeign;
import com.lemon.payment.service.WxPaymentNotifyService;
import com.lemon.payment.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName WxPaymentNotifyServiceImpl
 **/
@Service
public class WxPaymentNotifyServiceImpl implements WxPaymentNotifyService {
    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private OrderFeign orderFeign;


    @Override
    public void processPayNotify(String data) {
        Map<String, String> dataMap;
        try {
            dataMap = WXPayUtil.xmlToMap(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }

        WXPay wxPay = this.wxPaymentService.assembleWxPayConfig();
        boolean valid;
        try {
            valid = wxPay.isResponseSignatureValid(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        if (!valid) {
            throw new ServerErrorException(9999);
        }

        String returnCode = dataMap.get("return_code");
        String orderNo = dataMap.get("out_trade_no");
        String resultCode = dataMap.get("result_code");

        if (!returnCode.equals("SUCCESS")) {
            throw new ServerErrorException(9999);
        }
        if (!resultCode.equals("SUCCESS")) {
            throw new ServerErrorException(9999);
        }
        if (orderNo == null) {
            throw new ServerErrorException(9999);
        }

        orderFeign.deal(orderNo);
    }
}
