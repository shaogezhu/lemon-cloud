package com.lemon.oauth.pojo.cms;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserIdentityDO
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lemon_user_identity")
public class UserIdentityDO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 456555840105356178L;

    @TableId(value = "id")
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 认证类型，例如 username_password，用户名-密码认证
     */
    private String identityType;

    /**
     * 认证，例如 用户名
     */
    private String identifier;

    /**
     * 凭证，例如 密码
     */
    private String credential;
}
