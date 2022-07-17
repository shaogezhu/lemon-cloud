package com.lemon.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.oauth.pojo.cms.UserGroupDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CmsUserGroupMapper
 **/
public interface CmsUserGroupMapper extends BaseMapper<UserGroupDO> {

    int insertBatch(@Param("relations") List<UserGroupDO> relations);

    int deleteByUserId(@Param("user_id") Long userId);
}