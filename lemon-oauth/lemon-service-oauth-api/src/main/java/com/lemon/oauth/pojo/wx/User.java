package com.lemon.oauth.pojo.wx;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.lemon.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName User
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@TableName(value = "user", autoResultMap = true)
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143810L;
    @TableId
    private Long id;

    private String openid;

    private String nickname;

    private String email;

    private String mobile;

    private String password;

    @TableField("unify_uid")
    private Long unifyUid;

    @TableField(value = "wx_profile",typeHandler = FastjsonTypeHandler.class)
    private Map<String, Object> wxProfile;
}

