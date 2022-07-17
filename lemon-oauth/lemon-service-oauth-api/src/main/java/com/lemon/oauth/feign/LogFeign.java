package com.lemon.oauth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LogFeign
 **/
@FeignClient(name = "oauth", path = "/cms/log",contextId = "LogFeign")
public interface LogFeign {
    @RequestMapping("/create")
    void createLog(@RequestParam(name = "template") String template,
                   @RequestParam(name = "permission") String permission,
                   @RequestParam(name = "userId") Long userId,
                   @RequestParam(name = "username") String username,
                   @RequestParam(name = "method") String method,
                   @RequestParam(name = "path") String path,
                   @RequestParam(name = "status")Integer status);


}
