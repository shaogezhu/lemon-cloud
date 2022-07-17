package com.lemon.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.advice.mybatis.Page;
import com.lemon.entity.LocalUser;
import com.lemon.exception.NotFoundException;
import com.lemon.oauth.mapper.UserMapper;
import com.lemon.oauth.pojo.wx.User;
import com.lemon.oauth.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserServiceImpl
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void updateUserWxInfo(Map<String, Object> wxUser) {
        User user = userMapper.selectById(LocalUser.getUserId());
        user.setNickname(wxUser.get("nickName").toString());
        user.setWxProfile(wxUser);
        userMapper.updateById(user);
    }

    @Override
    public User findByOpenid(String openId) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("openid",openId));
    }

    @Override
    public Long insertByOpenid(String openid) {
        User use = User.builder().openid(openid).build();
        userMapper.insert(use);
        return use.getId();
    }


    @Override
    public User getParsedUserById(Long id) {
        User user = this.getBaseMapper().selectById(id);
        if (user == null) {
            throw new NotFoundException(120000);
        }
        return user;
    }

    @Override
    public IPage<User> search(Integer page, Integer count, String keyword) {
        Page<User> pager = new Page<>(page, count);
        keyword = "".equals(keyword) ? null : "%" + keyword + "%";
        IPage<User> iPage = this.getBaseMapper().searchCUserByKeyword(pager, keyword);
        return iPage;
    }

}
