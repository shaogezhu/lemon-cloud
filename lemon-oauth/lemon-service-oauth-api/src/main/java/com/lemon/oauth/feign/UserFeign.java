package com.lemon.oauth.feign;

import com.lemon.oauth.pojo.wx.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserFeign
 **/
@FeignClient(name = "oauth", path = "/user",contextId = "userFeign")
public interface UserFeign {
    @RequestMapping("/openid")
    User findByOpenid(@RequestParam(name = "openid") String openId);

    @RequestMapping("/openid/insert")
    Long insertByOpenid(@RequestParam(name = "openid") String openid);

    @RequestMapping("/{id}")
    User findById(@PathVariable(name = "id") Long uid);
}
