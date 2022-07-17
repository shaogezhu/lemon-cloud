package com.lemon.payment.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.lemon.entity.LocalUser;
import com.lemon.exception.ForbiddenException;
import com.lemon.exception.ParameterException;
import com.lemon.exception.ServerErrorException;
import com.lemon.order.feign.OrderFeign;
import com.lemon.order.pojo.Order;
import com.lemon.payment.core.HttpRequestProxy;
import com.lemon.payment.core.LemonWxPayConfig;
import com.lemon.payment.service.WxPaymentService;
import com.lemon.oauth.feign.UserFeign;
import com.lemon.oauth.pojo.wx.User;
import com.lemon.util.CommonUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName WxPaymentServiceImpl
 **/
@Service
public class WxPaymentServiceImpl implements WxPaymentService {

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private UserFeign userFeign;

    @Value("${lemon.order.pay-callback-host}")
    private String payCallbackHost;

    @Value("${lemon.order.pay-callback-path}")
    private String payCallbackPath;

    private static LemonWxPayConfig lemonWxPayConfig = new LemonWxPayConfig();

    @Override
    public Map<String, String> preOrder(Long oid) {
        Long uid = LocalUser.getUserId();
        User user = this.userFeign.findById(uid);
        if (user==null){
            throw new ForbiddenException(10004);
        }
        Order order = this.orderFeign.getOrderByUserId(oid,uid);
        if (order.needCancel()) {
            throw new ForbiddenException(50010);
        }
        WXPay wxPay = this.assembleWxPayConfig();
        Map<String, String> params = this.makePreOrderParams(order.getFinalTotalPrice(), order.getOrderNo(),user.getOpenid());
        Map<String, String> wxOrder;
        try {
            wxOrder = wxPay.unifiedOrder(params);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        if (this.unifiedOrderSuccess(wxOrder)) {
            this.orderFeign.updateOrderPrepayId(order.getId(), wxOrder.get("prepay_id"));
        }

        return this.makePaySignature(wxOrder);
    }

    private Map<String, String> makePaySignature(Map<String, String> wxOrder) {
        Map<String, String> wxPayMap = new HashMap<>();
        String packages = "prepay_id=" + wxOrder.get("prepay_id");

        wxPayMap.put("appId", WxPaymentServiceImpl.lemonWxPayConfig.getAppID());
        wxPayMap.put("timeStamp", CommonUtil.timestamp10());
        wxPayMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(32));
        wxPayMap.put("package", packages);
        wxPayMap.put("signType", "MD5");

        String sign;
        try {
            sign = WXPayUtil.generateSignature(wxPayMap, WxPaymentServiceImpl.lemonWxPayConfig.getKey(), WXPayConstants.SignType.MD5);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }

        Map<String, String> miniPayParams = new HashMap<>();

        miniPayParams.put("paySign", sign);
        miniPayParams.putAll(wxPayMap);
        miniPayParams.remove("appId");
        return miniPayParams;
    }

    private boolean unifiedOrderSuccess(Map<String, String> wxOrder) {
        if (!"SUCCESS".equals(wxOrder.get("return_code"))
                || !"SUCCESS".equals(wxOrder.get("result_code"))) {
            throw new ParameterException(10007);
        }
        return true;
    }

    private Map<String, String> makePreOrderParams(BigDecimal serverFinalPrice, String orderNo,String openid) {
        String payCallbackUrl = this.payCallbackHost + this.payCallbackPath;
        Map<String, String> data = new HashMap<>();
        data.put("body", "Lemon商城");
        data.put("out_trade_no", orderNo);
        data.put("device_info", "Lemon");
        data.put("fee_type", "CNY");
        data.put("trade_type", "JSAPI");

        data.put("total_fee", CommonUtil.yuanToFenPlainString(serverFinalPrice));
        data.put("openid", openid);
        data.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());

        data.put("notify_url", payCallbackUrl);
        return data;
    }

    @Override
    public WXPay assembleWxPayConfig() {
        WXPay wxPay;
        try {
            wxPay = new WXPay(WxPaymentServiceImpl.lemonWxPayConfig);
        } catch (Exception ex) {
            throw new ServerErrorException(9999);
        }
        return wxPay;
    }
}
