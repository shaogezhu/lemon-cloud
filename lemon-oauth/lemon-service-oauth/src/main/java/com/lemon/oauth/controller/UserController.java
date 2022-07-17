package com.lemon.oauth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.interceptors.ScopeLevel;
import com.lemon.oauth.pojo.wx.User;
import com.lemon.oauth.service.UserService;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
import io.github.talelin.core.annotation.AdminRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserController
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ScopeLevel
    @PostMapping("/wx_info")
    public ResponseEntity.BodyBuilder updateUserInfo(@RequestBody Map<String,Object> user) {
        userService.updateUserWxInfo(user);
        return ResponseEntity.ok();
    }

    @RequestMapping("/openid")
    public User findByOpenid(@RequestParam(name = "openid") String openid){
        return userService.findByOpenid(openid);
    }

    @RequestMapping("/openid/insert")
    public Long insertByOpenid(@RequestParam(name = "openid") String openid){
        return userService.insertByOpenid(openid);
    }

    @RequestMapping("/{id}")
    public User findById(@PathVariable(name = "id") Long uid){
        return userService.getById(uid);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable @Positive(message = "{id}") Long id) {
        return ResponseEntity.ok(userService.getParsedUserById(id));
    }

    @GetMapping("/page")
    @AdminRequired
    public ResponseEntity<PageResponseVO<User>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Integer page
    ) {
        Page<User> paging = userService.page(new Page<User>(count, page));
        return ResponseEntity.ok(PageUtil.build(paging));
    }

    @GetMapping("/search")
    @AdminRequired
    public ResponseEntity<PageResponseVO<User>> search(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Integer page,
            @RequestParam(name = "keyword") String keyword
    ) {
        IPage<User> paging = userService.search(page, count, keyword);
        return ResponseEntity.ok(PageUtil.build(paging));
    }

}
