package com.lemon.payment.controller;

import com.lemon.interceptors.ScopeLevel;
import com.lemon.payment.core.LemonWxNotify;
import com.lemon.payment.service.WxPaymentNotifyService;
import com.lemon.payment.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PaymentController
 **/
@RestController
@RequestMapping("/payment")
@Validated
public class PaymentController {

    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private WxPaymentNotifyService wxPaymentNotifyService;

    @ScopeLevel
    @PostMapping("/pay/order/{id}")
    public ResponseEntity<Map<String, String>> preWxOrder(@PathVariable(name = "id") @Positive Long oid) {
        return ResponseEntity.ok(this.wxPaymentService.preOrder(oid));
    }

    @RequestMapping("/wx/notify")
    public ResponseEntity<String> payCallback(HttpServletRequest request) {
        InputStream s;
        try {
            s = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(LemonWxNotify.fail());
        }
        String xml;
        xml = LemonWxNotify.readNotify(s);
        try {
            this.wxPaymentNotifyService.processPayNotify(xml);
        } catch (Exception e) {
            return ResponseEntity.ok(LemonWxNotify.fail());
        }
        return ResponseEntity.ok(LemonWxNotify.success());
    }
}
