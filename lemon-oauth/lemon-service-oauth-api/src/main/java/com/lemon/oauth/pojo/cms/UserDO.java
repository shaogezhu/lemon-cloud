package com.lemon.oauth.pojo.cms;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserDO
 **/
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("lemon_user")
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1463999384554707735L;

    @TableId(value = "id")
    private Long id;
    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

}
