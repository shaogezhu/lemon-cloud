package com.lemon.payment.core;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LemonWxPayConfig
 **/
public class LemonWxPayConfig implements WXPayConfig {

    /**
     * 小程序的AppId
     */
    @Override
    public String getAppID() {
        return "wxd*************95";
    }

    /**
     * 商户号
     */
    @Override
    public String getMchID() {
        return "16******91";
    }

    /**
     * api密钥
     */
    @Override
    public String getKey() {
        return "89**************************9d";
    }


    @Override
    public InputStream getCertStream() {
        return null;
    }
    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }
    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
