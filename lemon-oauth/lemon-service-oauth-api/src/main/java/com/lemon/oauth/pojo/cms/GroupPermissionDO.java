package com.lemon.oauth.pojo.cms;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GroupPermissionDO
 **/
@Data
@TableName("lemon_group_permission")
public class GroupPermissionDO implements Serializable {

    private static final long serialVersionUID = -358487811336536495L;

    @TableId(value = "id")
    private Long id;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 权限id
     */
    private Long permissionId;

    public GroupPermissionDO(Long groupId, Long permissionId) {
        this.groupId = groupId;
        this.permissionId = permissionId;
    }
}
