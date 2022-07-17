package com.lemon.oauth.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PermissionDO
 **/
@Data
@Builder
@TableName("lemon_permission")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2400022443732120128L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 权限名称，例如：访问首页
     */
    private String name;

    /**
     * 权限所属模块，例如：人员管理
     */
    private String module;

    /**
     * 0：关闭 1：开启
     */
    private Boolean mount;

}
