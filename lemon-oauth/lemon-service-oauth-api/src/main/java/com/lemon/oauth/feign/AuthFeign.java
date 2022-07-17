package com.lemon.oauth.feign;

import com.lemon.oauth.config.CmsProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName AuthFeign
 **/
@FeignClient(name = "oauth", path = "/client",contextId = "authFeign")
public interface AuthFeign {
    @GetMapping("/key")
    String getSecretKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

    @RequestMapping("/jwt/key")
    CmsProperties getSecretJwtKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
}
