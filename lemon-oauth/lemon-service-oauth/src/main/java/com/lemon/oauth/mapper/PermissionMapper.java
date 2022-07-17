package com.lemon.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.oauth.pojo.cms.PermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PermissionMapper
 **/
public interface PermissionMapper extends BaseMapper<PermissionDO> {

    /**
     * 通过分组ids得到所有分组下的权限
     *
     * @param groupIds 分组ids
     * @return 权限
     */
    List<PermissionDO> selectPermissionsByGroupIds(@Param("groupIds") List<Long> groupIds);

    /**
     * 通过分组id得到所有分组下的权限
     *
     * @param groupId 分组id
     * @return 权限
     */
    List<PermissionDO> selectPermissionsByGroupId(@Param("groupId") Long groupId);

    /**
     * 通过分组ids得到所有分组下的权限
     *
     * @param groupIds 分组ids
     * @param module   权限模块
     * @return 权限
     */
    List<PermissionDO> selectPermissionsByGroupIdsAndModule(@Param("groupIds") List<Long> groupIds, @Param("module") String module);
}