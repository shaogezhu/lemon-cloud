package com.lemon.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.oauth.pojo.cms.UserDO;
import com.lemon.oauth.pojo.wx.User;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserMapper
 **/
public interface UserMapper extends BaseMapper<User> {
    /**
     * 模糊查询C端用户
     * @param pager   分页对象
     * @param keyword 关键字
     * @return IPage
     */
    IPage<User> searchCUserByKeyword(Page<User> pager, String keyword);


    /**
     * 查询用户名为$username的人数
     *
     * @param username 用户名
     * @return 人数
     */
    int selectCountByUsername(String username);

    /**
     * 查询用户id为$id的人数
     *
     * @param id 用户id
     * @return 人数
     */
    int selectCountById(Integer id);

    /**
     * 通过分组id分页获取用户数据
     *
     * @param pager   分页
     * @param groupId 分组id
     * @param rootGroupId 超级用户组id(不返回超级用户组的用户)
     * @return 分页数据
     */
    IPage<UserDO> selectPageByGroupId(Page pager, Integer groupId, Integer rootGroupId);
}
