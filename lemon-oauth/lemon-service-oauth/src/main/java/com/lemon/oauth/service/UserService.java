package com.lemon.oauth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.oauth.pojo.wx.User;

import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserService
 **/
public interface UserService extends IService<User> {
    /**
     * 更新当前用户的昵称
     * @param user wx中的用户新信息
     */
    void updateUserWxInfo(Map<String, Object> user);

    /**
     * 通过微信的openid，查询用户的信息
     * @param openId openid
     */
    User findByOpenid(String openId);

    /**
     * 根据微信的openid插入一条新的数据，并返回插入数据的id
     * @param openid openid
     */
    Long insertByOpenid(String openid);


    User getParsedUserById(Long id);

    IPage<User> search(Integer page, Integer count, String keyword);
}
