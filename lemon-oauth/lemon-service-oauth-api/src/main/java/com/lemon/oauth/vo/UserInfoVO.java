package com.lemon.oauth.vo;

import com.lemon.oauth.pojo.cms.GroupDO;
import com.lemon.oauth.pojo.cms.UserDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UserInfoVO
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    private Integer id;

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

    /**
     * 分组
     */
    private List<GroupDO> groups;

    public UserInfoVO(UserDO user, List<GroupDO> groups) {
        BeanUtils.copyProperties(user, this);
        this.groups = groups;
    }
}