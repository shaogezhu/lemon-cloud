package com.lemon.oauth.pojo.cms;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserGroupDO
 **/
@Data
@TableName("lemon_user_group")
public class UserGroupDO implements Serializable {

    private static final long serialVersionUID = -7219009955825484511L;

    @TableId(value = "id")
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分组id
     */
    private Long groupId;

    public UserGroupDO(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
