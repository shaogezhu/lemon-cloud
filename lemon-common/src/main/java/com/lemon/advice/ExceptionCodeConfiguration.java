package com.lemon.advice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ExceptionCodeConfiguration
 **/
@Component
@ConfigurationProperties(prefix = "lemon")
@PropertySource(value = "classpath:config/exception-code.properties")
public class ExceptionCodeConfiguration {
    private Map<Integer,String> codes = new HashMap<>();

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }

    public String getMessage(int code){
        return this.codes.get(code);
    }
}
