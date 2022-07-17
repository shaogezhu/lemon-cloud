package com.lemon.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.oauth.pojo.cms.GroupPermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GroupPermissionMapper
 **/
public interface GroupPermissionMapper extends BaseMapper<GroupPermissionDO> {

    int insertBatch(@Param("relations") List<GroupPermissionDO> relations);

    int deleteBatchByGroupIdAndPermissionId(@Param("groupId") Long groupId, @Param("permissionIds") List<Long> permissionIds);
}